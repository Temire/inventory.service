package org.temire.inventory.service.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.temire.inventory.service.data.model.Order;
import org.temire.inventory.service.data.model.Product;
import org.temire.inventory.service.data.repositories.ProductRepository;
import org.temire.inventory.service.rest.response.GenericResponseDTO;
import org.temire.inventory.service.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDTO> all(Pageable pageable) {
        GenericResponseDTO genericResponseDTO = productService.findAll(pageable);
        return new ResponseEntity<>(genericResponseDTO, genericResponseDTO.getStatus());
    }

    @GetMapping("/available")
    public ResponseEntity<GenericResponseDTO> available(Pageable pageable) {
        GenericResponseDTO genericResponseDTO = productService.findAllAvailable(pageable,0);
        return new ResponseEntity<>(genericResponseDTO, genericResponseDTO.getStatus());
    }

    @GetMapping("/id/{product_id}")
    public ResponseEntity<GenericResponseDTO> id(@PathVariable String product_id) {
        Optional<Product> seen = productRepository.findById(product_id);
        GenericResponseDTO response;
        if(seen.isPresent())
            response = new GenericResponseDTO("00", HttpStatus.OK, "Product updated Successfully!", seen.get());
        else
            response = new GenericResponseDTO("99", HttpStatus.NOT_FOUND, "No Such product found!", null);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/new")
    public ResponseEntity<GenericResponseDTO> create(@RequestBody Product product) {
        GenericResponseDTO response = new GenericResponseDTO();
        try{
            Product updated = productRepository.save(product);
            response = new GenericResponseDTO("00", HttpStatus.OK, "Product updated Successfully!", updated);

        }catch(Exception ex){
            response = new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED , "Error updating product!", ex.getMessage());
        }
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponseDTO> update(@RequestBody Product product) {
        GenericResponseDTO response = productService.update(product);
           return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/update-price/{product_id}/{price}")
    public ResponseEntity<GenericResponseDTO> updatePrice(@PathVariable String product_id, @PathVariable double price) {
        GenericResponseDTO response = productService.updatePrice(product_id, price);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/place-order")
    public ResponseEntity<GenericResponseDTO> placeOrder(@RequestBody Order order) {
        GenericResponseDTO response = productService.makeOrder(order);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
