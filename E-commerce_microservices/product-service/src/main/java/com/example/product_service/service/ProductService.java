package com.example.product_service.service;


import com.example.product_service.dao.ProductDao;
import com.example.product_service.dto.CategoryDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.ProductWithCategoryResponse;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.feign.CategoryClient;
import com.example.product_service.mapper.EntityConverter;
import com.example.product_service.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final EntityConverter<Product, ProductDto> productConverter;
    private final CategoryClient categoryClient;

    public ProductDto createProduct(ProductDto productDto){
        CategoryDto categoryDto = categoryClient.getById(productDto.getCategoryId()).getBody();
        if (categoryDto == null) {
            throw new IllegalArgumentException("Category with ID " + productDto.getCategoryId() + " does not exist.");
        }
        Product product = productConverter.dtoToEntity(productDto, Product.class);
        product.setCategoryId(categoryDto.getId());
        productDao.save(product);
        return productConverter.entityToDto(product, ProductDto.class);
    }

    public ProductDto getProductById(Long id){
        return productDao.findById(id)
                .map(product -> {
                   ProductDto productDto = productConverter.entityToDto(product, ProductDto.class);
                   productDto.setCategoryId(product.getCategoryId());
                   return productDto;
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    public ProductWithCategoryResponse getProductWithCategory(Long productId){

        ProductDto productDto = productDao.findById(productId)
                                          .map(product -> productConverter.entityToDto(product, ProductDto.class))
                                          .orElseThrow(() -> new ProductNotFoundException("product not found"));

        CategoryDto categoryDto = categoryClient.getById(productDto.getCategoryId()).getBody();

        ProductWithCategoryResponse response = new ProductWithCategoryResponse();
        response.setName(productDto.getName());
        response.setDescription(productDto.getDescription());
        response.setPrice(productDto.getPrice());
        response.setStockQuantity(productDto.getStockQuantity());
        response.setCategoryDescription(categoryDto.getDescription());
        response.setCategoryName(categoryDto.getName());
        return response;
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
