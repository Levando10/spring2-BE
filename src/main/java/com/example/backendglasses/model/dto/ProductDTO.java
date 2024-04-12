package com.example.backendglasses.model.dto;

import com.example.backendglasses.model.ImageProduct;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDTO {
    Long getIdProduct();
    String getNameProduct();
    Double getPrice();
    String getNameFac();
    List<ImageProduct> getImageUrl();
    String getImageMax();
    String getDescription();
    String getNameCategory();
    Integer getQuantity();
}
