package org.temire.inventory.service.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class GenericResponseDTO {

  private String code;

  @JsonIgnore
  private HttpStatus status;

  private String message;
  private Object data;

  private Map<String, Object> metadata;

  public GenericResponseDTO() {
  }

  public GenericResponseDTO(String code, HttpStatus status, String message, Object data) {
    this.code = code;
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public GenericResponseDTO(String code, HttpStatus status, String message, Object data, Map<String, Object> metadata) {
    this.code = code;
    this.status = status;
    this.message = message;
    this.data = data;
    this.metadata = metadata;
  }

  public GenericResponseDTO(String code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public GenericResponseDTO(String code, String message, Object data, Map<String, Object> metadata) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.metadata = metadata;
  }

  public GenericResponseDTO(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  public GenericResponseDTO(String code, Object data) {
    this.code = code;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  @Override
  public String toString() {
    return "GenericResponseDTO{" +
      "code='" + code + '\'' +
      ", status=" + status +
      ", message='" + message + '\'' +
      ", data=" + data +
      ", metadata=" + metadata +
      '}';
  }
}

