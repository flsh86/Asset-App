package com.example.category;

import com.example.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryTest {

    @Test
    void creatingCategoryWithIdExceedingLimitShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;
        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Category(exceed + 1, "TestName", "TestDescription")),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    Category category = new Category();
                    category.setId(exceed + 1);
                })
        );
    }

}