package com.example.app_user_service.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
