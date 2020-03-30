package com.example.repositories;

import com.example.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
//@DataJpaTest
public class Example {
    @Mock
    UserRepository userRepository;

    @Test
    void testExample() {
        //given
        given(userRepository.findAll()).willReturn(List.of(
                new User(1L, "1", "1", "1")
        ));

        //when
        List<User> users = userRepository.findAll();

        //then
        assertThat(users, hasSize(1));
    }
}
