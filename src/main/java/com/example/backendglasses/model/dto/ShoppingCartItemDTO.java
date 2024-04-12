package com.example.backendglasses.model.dto;

public interface ShoppingCartItemDTO {
    Byte getQuantity();
    String getNameProduct();
    Double getPriceUnit();
    String getImageProduct();
    String getAddress();
    String getImageMax();
}
