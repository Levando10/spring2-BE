package com.example.backendglasses.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    private LocalDateTime orderDate;
    @Column(length = 10)
    private String status;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
    private String addressShip;

    public ShoppingCart(User user, String status) {
        this.user = user;
        this.status = status;
    }

}
