package org.temire.inventory.service.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckedProduct implements Serializable {
    String product_id;
    int update_qty, current_qty;
    boolean isAvailable = true;
}
