package com.example.E_commerce_application.controller;

import com.example.E_commerce_application.dto.ProductDto;
import com.example.E_commerce_application.dto.StockUpdateRequest;
import com.example.E_commerce_application.response.ApiResponse;
import com.example.E_commerce_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto){
        return ResponseEntity.created(URI.create("/api/v1/products/create")).body(productService.createProduct(productDto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts(){
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts(),"List of Products", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(ApiResponse.success(productService.searchProducts(keyword),"List of filtered searched datas", null));
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> searchProducts(@PathVariable Long productId){
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(productId),"Founded Product!", null));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateProductStock(@RequestBody StockUpdateRequest request){
        productService.updateProductStock(request.getProductId(), request.getNewStock());
        return ResponseEntity.ok(ApiResponse.success(null , "Stock updated successfully", null));
    }

}
