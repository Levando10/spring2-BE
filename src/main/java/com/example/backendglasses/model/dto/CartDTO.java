package com.example.backendglasses.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;


public interface CartDTO {
    Long getIdCart();
    String getStatusCart();
    Long getTotalPrice();
    LocalDateTime getOrderDay();
    String getAddressShip();
}
