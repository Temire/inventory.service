package org.temire.inventory.service.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.temire.inventory.service.CustomException;
import org.temire.inventory.service.data.dto.CheckedProduct;
import org.temire.inventory.service.data.dto.OrderProduct;
import org.temire.inventory.service.data.model.Order;
import org.temire.inventory.service.data.model.Product;
import org.temire.inventory.service.data.repositories.ProductRepository;
import org.temire.inventory.service.kafka.producer.KafkaProducer;
import org.temire.inventory.service.rest.response.GenericResponseDTO;
import org.temire.inventory.service.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductServiceImpl implements ProductService {

    private Lock lockForUpdate = new ReentrantLock();
    private final ProductRepository productRepository;
    private final KafkaProducer kafkaProducer;

    public ProductServiceImpl(ProductRepository productRepository, KafkaProducer kafkaProducer) {
        this.productRepository = productRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void updateProductQuantity(String product_id, Integer update_qty) {
        lockForUpdate.lock();
        Optional<Product> optionalProduct = findById(product_id);
        if (optionalProduct.isPresent()) {
            Product p = optionalProduct.get();
            int current_qty = p.getQuantity();
            if (current_qty > update_qty) {
                int new_qty = current_qty - update_qty;
                p.setQuantity(new_qty);
                productRepository.save(p);
            } else throw new CustomException("The purchase quantity is higher than available products");
        }
        lockForUpdate.unlock();
    }

    @Override
    public GenericResponseDTO findAll(Pageable pageable) {
        GenericResponseDTO genericResponseDTO = new GenericResponseDTO("00", HttpStatus.OK, "Search Completed", productRepository.findAll(pageable));
        return genericResponseDTO;
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public GenericResponseDTO updatePrice(String product_id, double price) {
        Optional<Product> product = findById(product_id);
        if (product.isPresent()) {
            Product toBeUpdated = product.get();
            toBeUpdated.setPrice(price);
            Product updated = productRepository.save(toBeUpdated);
            GenericResponseDTO genericResponseDTO = new GenericResponseDTO("00", HttpStatus.OK, "Product price updated Successfully!", updated);
            return genericResponseDTO;
        } else
            return new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED, "No Product found with the ID");
    }

    @Override
    public GenericResponseDTO findAllAvailable(Pageable pageable, int qty) {
        List<Product> available =  productRepository.findWithCondition(pageable, qty);
        if(!available.isEmpty()) return new GenericResponseDTO("00", HttpStatus.OK, "Producs return successfully!", available);
        else return new GenericResponseDTO("11", HttpStatus.NO_CONTENT, "NO PRODUCTS AVAILABLE!", available);
    }


    public GenericResponseDTO update(Product p){
        try{
            Product updated = productRepository.save(p);
            GenericResponseDTO genericResponseDTO = new GenericResponseDTO("00", HttpStatus.OK, "Product updated Successfully!", updated);
            return genericResponseDTO;
        }catch(Exception ex){
            return new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED , "Error updating product!", ex.getMessage());        }
    }



    @Override
    public GenericResponseDTO makeOrder(Order order) {
        try{
            kafkaProducer.sendMessage(order);
            return new GenericResponseDTO("00", HttpStatus.OK, "Order sent successfully!", order);
        }catch(Exception ex){
            return new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED, ex.getMessage(), null);
        }
    }
}
