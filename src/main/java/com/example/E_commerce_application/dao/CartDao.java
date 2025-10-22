package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart,Long> {

}
