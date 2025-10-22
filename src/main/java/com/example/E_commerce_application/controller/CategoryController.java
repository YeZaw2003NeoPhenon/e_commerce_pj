package com.example.E_commerce_application.controller;

import com.example.E_commerce_application.dto.CategoryDto;
import com.example.E_commerce_application.response.ApiResponse;
import com.example.E_commerce_application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryDto>> create(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.created(URI.create("/api/v1/categories/create")).body(ApiResponse.success(categoryService.createCategory(categoryDto), "Category created successfully!", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getAllCategories(), "List of Categories", null));
    }

}