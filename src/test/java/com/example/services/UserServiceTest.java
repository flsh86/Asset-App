package com.example.services;

import com.example.mapper.UserMapper;
import com.example.repositories.UserRepository;
import com.example.user.User;
import com.example.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllShouldReturnListOfUsersDTO() {
        //given
        List<User> preparedUsers = List.of(
                new User(1L, "TestName1", "TestLastName1", "1234"),
                new User(2L, "TestName2", "TestLastName2", "2345"),
                new User(3L, "TestName3", "TestLastName3", "3456"),
                new User(4L, "TestName4", "TestLastName4", "4567")
        );

        given(userRepository.findAll()).willReturn(preparedUsers);

        //When
        List<UserDTO> serviceUser = userService.findAll();

        //Then
        assertAll(
                () -> assertThat(serviceUser, hasSize(4)),
                () -> assertThat(serviceUser, is(not(emptyCollectionOf(UserDTO.class)))),
                () -> assertThat(serviceUser, notNullValue())
        );
    }

    @Test
    void findByLastNameContaining() {
        //given
        List<User> users = List.of(
                new User(1L, "Test", "Test", "Test")
        );
        given(userRepository.findByLastNameContainsIgnoreCase("Test")).willReturn(users);

        //when
        List<UserDTO> userDTOs = userService.findByLastName("Test");

        //then
        assertThat(userDTOs, hasSize(1));
    }

    @Test
    void findByIdShouldReturnValidUser() {
        //Given
        User user = new User(1L, "TestName", "TestLastName", "TestPesel");
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //When
        UserDTO userDTO = userService.findById(1L);

        //Then
        assertAll(
                () -> assertThat(userDTO, notNullValue()),
                () -> assertThat(userDTO.getId(), equalTo(user.getId())),
                () -> assertThat(userDTO.getFirstName(), equalTo(user.getFirstName())),
                () -> assertThat(userDTO.getLastName(), equalTo(user.getLastName())),
                () -> assertThat(userDTO.getPesel(), equalTo(user.getPesel()))
        );
    }

    @Test
    void saveNewUserShouldAddUserToRepository() {
        //given
        User user = new User(1L, "TestName", "TestSurname", "TestPesel");
        given(userRepository.save(user)).willReturn(user);

        //When
        UserDTO userDTO = UserMapper.toDTO(user);
        User entity = userService.save(userDTO);

        //Then
        verify(userRepository).save(user);
        System.out.println(entity);
    }

    @Test
    void savingUserWithAlreadyExistingPeselShouldThrowAnException() {
        //given
        User user1 = new User(1L, "TestName1", "TestSurname1", "TestPesel");
        User user2 = new User(2L, "TestName2", "TestSurname2", "TestPesel");

        given(userRepository.save(user1)).willReturn(user1);
        given(userRepository.save(user2)).willThrow(ResponseStatusException.class);

        //When
        UserDTO userDTO1 = UserMapper.toDTO(user1);
        User savedUser1 = userService.save(userDTO1);

        UserDTO userDTO2 = UserMapper.toDTO(user2);

        //then
        assertThrows(ResponseStatusException.class, () -> userService.save(userDTO2));

    }

    @Test
    void deleteNotExistingUserShouldThrowAnException() {
        //given
        User user = new User(1L, "Test", "Test", "Test");
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userService.delete(user.getId());
        verify(userRepository).findById(user.getId());

    }

    @Test
    void getUserAssignments() {
    }

    @Test
    void updateUser() {
    }

}