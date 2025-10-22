package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.CategoryDao;
import com.example.E_commerce_application.dao.ProductDao;
import com.example.E_commerce_application.dto.ProductDto;
import com.example.E_commerce_application.exception.CategoryNotFoundException;
import com.example.E_commerce_application.exception.ProductNotFoundException;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.Category;
import com.example.E_commerce_application.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final EntityConverter<Product, ProductDto> productConverter;
    private final CategoryDao categoryDao;

    public ProductDto createProduct(ProductDto productDto){
        Category category = categoryDao.findById(productDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category Not Found!"));
        Product product = productConverter.dtoToEntity(productDto, Product.class);
        product.setCategory(category);
        productDao.save(product);
        return productConverter.entityToDto(product, ProductDto.class);
    }

    public ProductDto getProductById(Long id){
        return productDao.findById(id)
                .map(product -> {
                   ProductDto productDto = productConverter.entityToDto(product, ProductDto.class);
                   productDto.setCategoryId(product.getCategory().getId());
                   return productDto;
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    public List<ProductDto> getAllProducts(){
        return productDao.findAll()
                .stream()
                .map(product -> productConverter.entityToDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String keyword){
        return productDao
                .searchProducts(keyword)
                .stream()
                .map(product -> productConverter.entityToDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void updateProductStock(Long productId, int newStock){
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

       Product product = productDao.findById(productId)
                                         .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        product.setStockQuantity(newStock);
        productDao.save(product);
    }

}
