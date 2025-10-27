package com.example.product_service.feign;

import com.example.product_service.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryClient {

    @GetMapping("/api/v1/categories/all")
     ResponseEntity<List<CategoryDto>> getAll();

    @GetMapping("/api/v1/categories/get/{id}")
     ResponseEntity<CategoryDto> getById(@PathVariable Long id);
}
