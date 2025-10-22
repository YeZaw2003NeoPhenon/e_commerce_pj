package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {
}
