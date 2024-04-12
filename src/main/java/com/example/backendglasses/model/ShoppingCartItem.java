package com.example.backendglasses.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id")
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
    private Integer quantity;
    private Double unitPrice;

    public ShoppingCartItem(ShoppingCart shoppingCart, Product product, Double unitPrice, Integer quantity) {
        this.shoppingCart = shoppingCart;
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
