package com.example.E_commerce_application.config;

import com.example.E_commerce_application.jwt.JwtFilter;
import com.example.E_commerce_application.model.Role;
import com.example.E_commerce_application.security.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final AppUserService appUserService;

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(csrfConfigurer -> {
            csrfConfigurer.disable();
        })
        .httpBasic(Customizer.withDefaults())
         .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/v1/users/login").permitAll()
                            .requestMatchers(
                                    "/api/v1/carts/add-item-cart",
                                    "/api/v1/carts/remove-item-cart",
                                    "/api/v1/carts/all",
                                    "/api/v1/carts/calculate"
                                    ).hasRole(Role.ADMIN.name())
                            .requestMatchers("/api/v1/users/get/{userId}").hasRole(Role.ADMIN.name())
                            .requestMatchers("/api/v1/users/register").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                            .requestMatchers("/api/v1/orders/place-order/{userId}").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                            .requestMatchers("/api/v1/orders/cancel/{orderId}").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                            .requestMatchers("/api/v1/orders/get-user-order/{userId}").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                            .requestMatchers("/api/v1/orders/get/{orderId}").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                            .requestMatchers("/api/v1/categories/create").hasRole(Role.ADMIN.name())
                            .anyRequest().authenticated();
         })
        .sessionManagement(
                session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(daoAuthenticationProvider())
        .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(appUserService);
        return authenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

}
