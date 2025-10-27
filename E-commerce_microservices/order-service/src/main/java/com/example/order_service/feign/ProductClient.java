package com.example.order_service.feign;

import com.example.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-SERVICE")
public interface ProductClient {

   @GetMapping("/api/v1/products/get/{productId}")
   ResponseEntity<ProductDto> getProductById(@PathVariable Long productId);

}
