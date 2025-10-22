package com.example.E_commerce_application.exception;


public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }

}
