package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
