package com.example.cart_service.model;

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

    private Long productId;

    private int quantity;

    private BigDecimal priceSnapshot;

    public CartItem(Cart cart, Long productId, int quantity, BigDecimal priceSnapshot) {
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

}
