package com.example.E_commerce_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal priceSnapshot;

    public CartItem(Cart cart, Product product, int quantity, BigDecimal priceSnapshot) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

}
