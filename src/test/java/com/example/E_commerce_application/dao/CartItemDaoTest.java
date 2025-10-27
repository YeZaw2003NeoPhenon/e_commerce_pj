package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CartItemDaoTest {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private User user;
    private Cart cart;
    private Product product;

    private CartItem cartItem;

    private Category category;

    @BeforeEach
     void setUp(){
        user = new User("Neo","neo@gmail.com", Role.ADMIN);
        category = new Category("test", "testing...");
        userDao.save(user);
        categoryDao.save(category);

        product = new Product("test","testing...",new BigDecimal("500"), 5, category);

        productDao.save(product);


        cart = new Cart(new ArrayList<>(), user);
        cartDao.save(cart);

        cartItem = new CartItem(cart, product,5,new BigDecimal("5000"));
        cartItemDao.save(cartItem);
    }

    @Test
    void testFindByCartIdAndProductId(){
       CartItem result =  cartItemDao.findByCartIdAndProductId(cart.getId(),product.getId());

       assertThat(result).isNotNull();
       assertThat(result.getQuantity()).isEqualTo(5);
       assertThat(result.getCart().getId()).isEqualTo(cart.getId());
       assertThat(result.getProduct().getId()).isEqualTo(product.getId());
       assertThat(result.getPriceSnapshot()).isEqualTo(new BigDecimal("5000"));
    }

}