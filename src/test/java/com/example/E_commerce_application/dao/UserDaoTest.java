package com.example.E_commerce_application.dao;

import com.example.E_commerce_application.model.Role;
import com.example.E_commerce_application.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserDaoTest {
    private User user;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setName("Neo");
        user.setEmail("neo@gmail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(new BCryptPasswordEncoder().encode("neoneo"));
        user.setOrders(Collections.emptyList());
        user.setCart(null);
        userDao.save(user);
    }

    @Test
    void testFindUserByEmail(){
        User foundedUser = userDao.findUserByEmail(user.getEmail()).orElseThrow();
        assertThat(foundedUser.getName()).isEqualTo(user.getName());
        assertThat(foundedUser.getEmail()).isEqualTo(user.getEmail());
        String password = "neoneo";
        assertThat(new BCryptPasswordEncoder().matches(password, foundedUser.getPassword())).isTrue();
    }

}