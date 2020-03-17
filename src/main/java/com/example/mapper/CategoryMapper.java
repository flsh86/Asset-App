package com.example.mapper;

import com.example.category.Category;
import com.example.category.CategoryDTO;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category entity) {
        return new CategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public static Category toEntity(CategoryDTO dto) {
        return new Category(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }

}
