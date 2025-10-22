package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem,Long>{

   CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
