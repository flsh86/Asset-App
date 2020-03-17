package com.example.mapper;

import com.example.category.Category;
import com.example.category.CategoryDTO;
import com.example.user.User;
import com.example.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryMaperTest {

    @Test
    void mappingCategoryEntityToDTOShouldReturnValidCategoryDTO() {
        //Given
        Category category = new Category();

        //When
        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);

        //Then
        assertAll(
                () -> assertThat(category.getId(), equalTo(categoryDTO.getId())),
                () -> assertThat(category.getName(), equalTo(categoryDTO.getName())),
                () -> assertThat(category.getDescription(), equalTo(categoryDTO.getDescription()))
        );
    }

    @Test
    void mappingCategoryDTOToEntityShouldReturnValidCategory() {
        //Given
        CategoryDTO categoryDTO = new CategoryDTO();

        //When
        Category category = CategoryMapper.toEntity(categoryDTO);

        //Then
        assertAll(
                () -> assertThat(categoryDTO.getId(), equalTo(category.getId())),
                () -> assertThat(categoryDTO.getName(), equalTo(category.getName())),
                () -> assertThat(categoryDTO.getDescription(), equalTo(category.getDescription()))
        );
    }

}
