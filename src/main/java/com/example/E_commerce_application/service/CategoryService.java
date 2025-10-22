package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.CategoryDao;
import com.example.E_commerce_application.dto.CategoryDto;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final EntityConverter<Category,CategoryDto> categoryConverter;


    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryConverter.dtoToEntity(categoryDto, Category.class);
        categoryDao.save(category);
        return categoryConverter.entityToDto(category, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryDao
                .findAll()
                .stream()
                .map(category -> categoryConverter.entityToDto(category,CategoryDto.class))
                .collect(Collectors.toList());
    }

}
