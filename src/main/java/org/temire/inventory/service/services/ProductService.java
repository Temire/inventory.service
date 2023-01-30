package org.temire.inventory.service.services;

import org.springframework.data.domain.Pageable;
import org.temire.inventory.service.CustomException;
import org.temire.inventory.service.data.dto.OrderProduct;
import org.temire.inventory.service.data.model.Order;
import org.temire.inventory.service.data.model.Product;
import org.temire.inventory.service.rest.response.GenericResponseDTO;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    void updateProductQuantity(String product_id, Integer update_qty);

    GenericResponseDTO findAll(Pageable pageable);

    GenericResponseDTO update(Product p);

    Optional<Product> findById(String id);

    GenericResponseDTO updatePrice(String product_id, double price);

    GenericResponseDTO findAllAvailable(Pageable pageable, int qty);

    GenericResponseDTO makeOrder(Order order);
}
