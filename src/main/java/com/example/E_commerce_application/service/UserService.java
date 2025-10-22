package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.dto.LoginRequest;
import com.example.E_commerce_application.dto.UserRequest;
import com.example.E_commerce_application.dto.UserResponse;
import com.example.E_commerce_application.exception.UserNotFoundException;
import com.example.E_commerce_application.jwt.JwtUtils;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.Role;
import com.example.E_commerce_application.model.User;
import com.example.E_commerce_application.security.AppUser;
import com.example.E_commerce_application.security.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final EntityConverter<User, UserResponse> userEntityConverter;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserResponse registerUser(UserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        userDao.save(user);
        return userEntityConverter.entityToDto(user, UserResponse.class);
    }

    public UserResponse getUserById(Long id){
        User user = userDao.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return userEntityConverter.entityToDto(user, UserResponse.class);
    }

    public String login(LoginRequest loginRequest){

       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
       );

      AppUser appUser = (AppUser) authentication.getPrincipal();

      if(appUser == null){
          throw new IllegalArgumentException("Invalid User credentials!");
      }

      Role role = appUser.getUser().getRole();

      return jwtUtils.generateToken(appUser.getUser().getId(),appUser.getUsername(), role);
    }

}
