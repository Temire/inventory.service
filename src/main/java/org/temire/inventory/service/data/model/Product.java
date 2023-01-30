package org.temire.inventory.service.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Product{

    @Id
    private String product_id;

    @Field
    String name, description;
    @Field
    double price;
    @Field
    Integer quantity;
}
