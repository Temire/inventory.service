package org.temire.inventory.service.data.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.temire.inventory.service.data.model.Product;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'quantity': {$gte:?0} }")
    List<Product>findWithCondition(Pageable pageable, int quantity);


}