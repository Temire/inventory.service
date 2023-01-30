package org.temire.inventory.service.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.temire.inventory.service.data.dto.OrderProduct;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Order{

    @MongoId
    private String order_id;

    @Field
    List<OrderProduct> items;

    @Field
    double total_order_value;

    @Field
    LocalDate order_date, fulfillment_date;

    @Field
    String delivery_address, customer_name, customer_email, customer_phone;

    @Field
    boolean fulfilled;

}
