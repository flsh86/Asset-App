package com.example.user;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void mappingUserEntityToDTOShouldReturnValidUserDTO() {
        //Given
        User user = new User(99999L, "TestUserName", "TestUserLastName", "TestPesel");

        //When
        UserDTO userDTO = UserMapper.toDTO(user);

        //Then
        assertAll(
                () -> assertThat(user.getId(), equalTo(userDTO.getId())),
                () -> assertThat(user.getFirstName(), equalTo(userDTO.getFirstName())),
                () -> assertThat(user.getLastName(), equalTo(userDTO.getLastName())),
                () -> assertThat(user.getPesel(), equalTo(userDTO.getPesel()))
        );
    }

    @Test
    void mappingUserDTOToEntityShouldReturnValidUser() {
        //Given
        UserDTO userDTO = new UserDTO(99999L, "TestUserDTOName", "TestUserDTOLastName", "TestPesel");

        //When
        User user = UserMapper.toEntity(userDTO);

        //Then
        assertAll(
                () -> assertThat(userDTO.getId(), equalTo(user.getId())),
                () -> assertThat(userDTO.getFirstName(), equalTo(user.getFirstName())),
                () -> assertThat(userDTO.getLastName(), equalTo(user.getLastName())),
                () -> assertThat(userDTO.getPesel(), equalTo(user.getPesel()))
        );
    }
}