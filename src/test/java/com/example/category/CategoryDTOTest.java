package com.example.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryDTOTest {

    @Test
    void creatingCategoryWithIdExceedingLimitShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;
        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new CategoryDTO(exceed + 1, "TestName", "TestDescription")),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(exceed + 1);
                })
        );
    }
}
