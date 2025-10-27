package com.example.category_service.service;

import com.example.category_service.dao.CategoryDao;
import com.example.category_service.dto.CategoryDto;
import com.example.category_service.exception.CategoryNotFoundException;
import com.example.category_service.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.category_service.mapper.EntityConverter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final EntityConverter<Category, CategoryDto> categoryConverter;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryConverter.dtoToEntity(categoryDto, Category.class);
        categoryDao.save(category);
        return categoryConverter.entityToDto(category, CategoryDto.class);
    }

    public CategoryDto getCategoryById(Long id){
       return categoryDao.findById(id)
               .stream()
               .map(category -> categoryConverter.entityToDto(category, CategoryDto.class))
               .findFirst()
               .orElseThrow(() -> new CategoryNotFoundException("category is not found"));
    }


    public List<CategoryDto> getAllCategories() {
        return categoryDao
                .findAll()
                .stream()
                .map(category -> categoryConverter.entityToDto(category,CategoryDto.class))
                .collect(Collectors.toList());
    }

}
