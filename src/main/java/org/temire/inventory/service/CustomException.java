package org.temire.inventory.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.temire.inventory.service.data.dto.CheckedProduct;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CustomException extends RuntimeException {

    String message;
    HttpStatus httpStatus;
    List<CheckedProduct> productExceedingPurchassbleQTY;

    public CustomException (String message){
        this.message= message;
    }

    public CustomException(String message, Throwable cause, String message1, HttpStatus httpStatus) {
        super(message, cause);
        this.message = message1;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, List<CheckedProduct> productExceedingPurchassbleQTY) {
        super(message);
        this.message = message;
        this.productExceedingPurchassbleQTY = productExceedingPurchassbleQTY;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
