package com.example.E_commerce_application.security;

import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userDao.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
       return new AppUser(user);
    }


}
