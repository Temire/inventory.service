package org.temire.inventory.service.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderProduct implements Serializable {
    String product_id;
    String name;
    String description;
    double orderPrice;
    Integer quantity;
}
