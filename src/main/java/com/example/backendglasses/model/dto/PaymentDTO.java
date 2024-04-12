package com.example.backendglasses.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
    String fullName;
    String email;
    String phoneNumber;
    String address;
    Long totalPayment;
    Long idAccount;
}
