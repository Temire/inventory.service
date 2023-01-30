package org.temire.inventory.service.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.temire.inventory.service.data.dto.OrderProduct;
import org.temire.inventory.service.data.model.Order;
import org.temire.inventory.service.data.model.Product;
import org.temire.inventory.service.data.repositories.ProductRepository;
import org.temire.inventory.service.kafka.producer.KafkaProducer;
import org.temire.inventory.service.rest.response.GenericResponseDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private KafkaProducer mockKafkaProducer;

    private ProductServiceImpl productServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        productServiceImplUnderTest = new ProductServiceImpl(mockProductRepository, mockKafkaProducer);
    }

    @Test
    void testUpdateProductQuantity() {
        // Setup
        // Configure ProductRepository.findById(...).
        final Optional<Product> optionalProduct = Optional.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findById("id")).thenReturn(optionalProduct);

        // Configure ProductRepository.save(...).
        final Product product = new Product("product_id", "name", "description", 0.0, 0);
        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0))).thenReturn(product);

        // Run the test
        productServiceImplUnderTest.updateProductQuantity("id", 0);

        // Verify the results
        verify(mockProductRepository).save(new Product("product_id", "name", "description", 0.0, 0));
    }

    @Test
    void testUpdateProductQuantity_ProductRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockProductRepository.findById("id")).thenReturn(Optional.empty());

        // Configure ProductRepository.save(...).
        final Product product = new Product("product_id", "name", "description", 0.0, 0);
        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0))).thenReturn(product);

        // Run the test
        productServiceImplUnderTest.updateProductQuantity("id", 0);

        // Verify the results
        verify(mockProductRepository).save(new Product("product_id", "name", "description", 0.0, 0));
    }

    @Test
    void testUpdateProductQuantity_ProductRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ProductRepository.findById(...).
        final Optional<Product> optionalProduct = Optional.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findById("id")).thenReturn(optionalProduct);

        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0)))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> productServiceImplUnderTest.updateProductQuantity("id", 0))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure ProductRepository.findAll(...).
        final Page<Product> products = new PageImpl<>(
                List.of(new Product("product_id", "name", "description", 0.0, 0)));
        when(mockProductRepository.findAll(any(Pageable.class))).thenReturn(products);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.findAll(PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testFindAll_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockProductRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.findAll(PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testFindById() {
        // Setup
        final Optional<Product> expectedResult = Optional.of(new Product("product_id", "name", "description", 0.0, 0));

        // Configure ProductRepository.findById(...).
        final Optional<Product> optionalProduct = Optional.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findById("id")).thenReturn(optionalProduct);

        // Run the test
        final Optional<Product> result = productServiceImplUnderTest.findById("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_ProductRepositoryReturnsAbsent() {
        // Setup
        when(mockProductRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final Optional<Product> result = productServiceImplUnderTest.findById("id");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testUpdatePrice() {
        // Setup
        // Configure ProductRepository.findById(...).
        final Optional<Product> optionalProduct = Optional.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findById("id")).thenReturn(optionalProduct);

        // Configure ProductRepository.save(...).
        final Product product = new Product("product_id", "name", "description", 0.0, 0);
        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0))).thenReturn(product);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.updatePrice("id", 0.0);

        // Verify the results
    }

    @Test
    void testUpdatePrice_ProductRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockProductRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.updatePrice("id", 0.0);

        // Verify the results
    }

    @Test
    void testUpdatePrice_ProductRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ProductRepository.findById(...).
        final Optional<Product> optionalProduct = Optional.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findById("id")).thenReturn(optionalProduct);

        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0)))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> productServiceImplUnderTest.updatePrice("id", 0.0))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testFindAllAvailable() {
        // Setup
        // Configure ProductRepository.findWithCondition(...).
        final List<Product> products = List.of(new Product("product_id", "name", "description", 0.0, 0));
        when(mockProductRepository.findWithCondition(any(Pageable.class), eq(0))).thenReturn(products);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.findAllAvailable(PageRequest.of(0, 1), 0);

        // Verify the results
    }

    @Test
    void testFindAllAvailable_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockProductRepository.findWithCondition(any(Pageable.class), eq(0))).thenReturn(Collections.emptyList());

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.findAllAvailable(PageRequest.of(0, 1), 0);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final Product p = new Product("product_id", "name", "description", 0.0, 0);

        // Configure ProductRepository.save(...).
        final Product product = new Product("product_id", "name", "description", 0.0, 0);
        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0))).thenReturn(product);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.update(p);

        // Verify the results
    }

    @Test
    void testUpdate_ProductRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Product p = new Product("product_id", "name", "description", 0.0, 0);
        when(mockProductRepository.save(new Product("product_id", "name", "description", 0.0, 0)))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.update(p);

        // Verify the results
    }

    @Test
    void testMakeOrder() {
        // Setup
        final Order order = new Order("order_id",
                List.of(new OrderProduct("product_id", "name", "description", 0.0, 0)), 0.0, LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1), "delivery_address", "customer_name", "customer_email", "customer_phone",
                false);

        // Run the test
        final GenericResponseDTO result = productServiceImplUnderTest.makeOrder(order);

        // Verify the results
        verify(mockKafkaProducer).sendMessage(
                new Order("order_id", List.of(new OrderProduct("product_id", "name", "description", 0.0, 0)), 0.0,
                        LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "delivery_address", "customer_name",
                        "customer_email", "customer_phone", false));
    }
}
