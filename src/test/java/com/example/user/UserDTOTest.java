package com.example.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDTOTest {

    @Test
    void creatingUserWithIdExceedingLimitShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;
        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new UserDTO(exceed + 1, "Test", "Test", "Test")),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    UserDTO user = new UserDTO();
                    user.setId(exceed + 1);
                })
        );
    }

}
