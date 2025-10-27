package com.example.app_user_service.service;

import com.example.app_user_service.dao.UserDao;
import com.example.app_user_service.dto.UserRequest;
import com.example.app_user_service.dto.UserResponse;
import com.example.app_user_service.exception.UserNotFoundException;
import com.example.app_user_service.mapper.EntityConverter;
import com.example.app_user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final EntityConverter<User, UserResponse> userEntityConverter;

    public UserResponse registerUser(UserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(request.getPassword());
        userDao.save(user);
        return userEntityConverter.entityToDto(user, UserResponse.class);
    }

    public UserResponse getUserById(Long id){
        User user = userDao.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return userEntityConverter.entityToDto(user, UserResponse.class);
    }

    @Transactional
    public void addOrderToUser(Long userId, Long orderId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (user.getOrderIds() == null) {
            user.setOrderIds(new ArrayList<>());
        }
        user.getOrderIds().add(orderId);
        userDao.save(user);
    }

    public List<Long> getUserOrderIds(Long userId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return user.getOrderIds();
    }

    @Transactional
    public void setCartUser(Long userId, Long cartId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setCartId(cartId);
        userDao.save(user);
    }

    public Long getUserCartId(Long userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return user.getCartId();
    }

}