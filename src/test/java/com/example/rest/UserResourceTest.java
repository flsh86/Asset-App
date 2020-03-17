package com.example.rest;

import com.example.repositories.UserRepository;
import com.example.services.UserService;
import com.example.user.User;
import com.example.user.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserResource.class)
class UserResourceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllWithoutParamShouldReturnListOfAllExistingUsers() throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1L, "Test1", "Test1", "Test1"),
                new UserDTO(2L, "Test2", "Test2", "Test2")
        );

        given(userService.findAll()).willReturn(users);

         mvc.perform(get("/api/users"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(jsonPath("$", hasSize(2)))
                 .andExpect(jsonPath("$[0].id", is(1)))
                 .andExpect(jsonPath("$[0].firstName", is("Test1")))
                 .andExpect(jsonPath("$[1].id", is(2)))
                 .andExpect(jsonPath("$[1].firstName", is("Test2")));

         verify(userService, times(1)).findAll();
         verifyNoMoreInteractions(userService);
    }

    @Test
    void findAllWithParamShouldReturnListOfMatchingUsers() throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1L, "Test1", "Test1", "Test1"),
                new UserDTO(2L, "Test2", "Test2", "Test2")
        );

        given(userService.findByLastName("Test")).willReturn(users);

        mvc.perform(get("/api/users?lastName=", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Test1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Test2")));
    }


    @Test
    void findAllWillThrowAnException() throws Exception {
        given(userService.findAll()).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/api/users"))
                .andExpect(status().is(404));
    }


    @Test
    void findByLastNameWillThrowAnException() throws Exception {
        given(userService.findByLastName("Test")).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/api/users?lastName=Test"))
                .andExpect(status().is(404));

        verify(userService, times(1)).findByLastName("Test");
    }

    @Test
    void addUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "Test", "Test", "Test");

        mvc.perform(
                post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/api/users/1")));

        verify(userService, times(1)).save(userDTO);
    }

    @Test
    void addUserWithAlreadyExistingPeselShouldThrowAnException() throws Exception {
//        UserDTO userDTO1 = new UserDTO(1L, "Test1", "Test1", "Test1");
        UserDTO userDTO2 = new UserDTO(2L, "Test2", "Test2", "Test1");

        given(userService.save(userDTO2)).willThrow(new ResponseStatusException(HttpStatus.CONFLICT));

//        mvc.perform(
//                post("/api/users")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(userDTO1)))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location", containsString("/api/users/1")));

        mvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO2)))
                .andExpect(status().isConflict());

    }

    @Test
    void findUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "Test", "Test", "Test");

        given(userService.findById(userDTO.getId())).willReturn(userDTO);

        mvc.perform(get("/api/users/{id}", userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Test")));
    }


    @Test
    void findUserThrows404Exception() throws Exception {
        given(userService.findById(10L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/api/users/{id}", 10))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(10L);
    }

    @Test
    void updateUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "Test", "Test", "Test");

    }

    @Test
    void getUserAssignments() {
    }

    @Test
    void deleteUser() {
    }

}