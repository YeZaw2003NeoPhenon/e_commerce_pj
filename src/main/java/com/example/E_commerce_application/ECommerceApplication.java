package com.example.E_commerce_application;

import com.example.E_commerce_application.dao.CartItemDao;
import com.example.E_commerce_application.dao.CategoryDao;
import com.example.E_commerce_application.dao.ProductDao;
import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(CartItemDao cartItemDao,
//											   UserDao userDao,
//											   ProductDao productDao,
//											   CategoryDao categoryDao) {
//		return args -> {
//			User user = new User();
//			user.setName("Leon");
//			user.setEmail("leon@example.com");
//			user.setPassword(new BCryptPasswordEncoder().encode("leonleon"));
//			user.setRole(Role.USER);
//
//			Cart cart = new Cart();
//			cart.setUser(user);
//			user.setCart(cart);
//			userDao.save(user);
//
//			Category category = new Category();
//			category.setName("Educational Equipments");
//			category.setDescription("Pens and pencils");
//			categoryDao.save(category);
//
//
//			Product p1 = new Product();
//			p1.setName("blash...");
//			p1.setPrice(BigDecimal.valueOf(950));
//			p1.setCategory(category);
//			productDao.save(p1);
//
//			CartItem cartItem = new CartItem();
//			cartItem.setQuantity(50);
//			cartItem.setProduct(p1);
//			cartItem.setCart(cart);
//			cartItem.setPriceSnapshot(BigDecimal.valueOf(950));
//
//			cartItemDao.save(cartItem);
//		};
//	}

}
