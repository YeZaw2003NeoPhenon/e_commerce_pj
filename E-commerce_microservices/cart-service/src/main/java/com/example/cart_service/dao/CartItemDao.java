package com.example.cart_service.dao;

import com.example.cart_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem,Long>{

   CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
