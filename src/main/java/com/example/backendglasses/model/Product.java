package com.example.backendglasses.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "CategoryId",referencedColumnName = "id")
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageProduct> imageProducts;

    @ManyToOne
    @JoinColumn(name = "manufacturerId",referencedColumnName = "id")
    private Manufacturer manufacturer ;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    public Product(Long id) {
        this.id = id;
    }

}
