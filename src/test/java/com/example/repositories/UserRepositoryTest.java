package com.example.repositories;

import com.example.repositories.UserRepository;
import com.example.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createdUserShouldBeAddedToRepository() {
        //Given
        User user = new User(10L, "TestName", "TestLastName", "01234567890");
        testEntityManager.persistAndFlush(user);

        //When
        Optional<User> found = userRepository.findById(user.getId());

        //Then
        if(found.isEmpty()) {
            assert false;
        } else {
            assertAll(
                    () -> assertThat(user.getId(), equalTo(found.get().getId())),
                    () -> assertThat(user.getFirstName(), equalTo(found.get().getFirstName())),
                    () -> assertThat(user.getLastName(), equalTo(found.get().getLastName())),
                    () -> assertThat(user.getPesel(), equalTo(found.get().getPesel()))
            );
        }
    }

    @Test
    void addingUserWithIdOfAlreadyExistingUserShouldThrowAnException() {
        //Given
        User user1 = new User(99999L, "TestUser#1", "TestUser#1", "TestPesel#1");
        User user2 = new User(99999L, "TestUser#2", "TestUser#2", "TestPesel#2");

        //When
        testEntityManager.persistAndFlush(user1);

        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(user2));
    }

    @Test
    void addingUserWithPeselOfAlreadyExistingUserShouldThrowAnException() {
        //Given
        User existingUser = userRepository.getOne(1L);
        User newUser = new User(99999L, "TestUser", "TestUser", existingUser.getPesel());

        //When
        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(newUser));
    }

    @Test
    void addingUserWithNameLongerThan255CharactersShouldThrowAnException() {
        //Given
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 256; i++) {
            str.insert(i, "a");
        }

        User user = new User(99999L, str.toString(), "TestLastName", "TestPesel");

        //Then
        //When
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(user));
    }

    @Test
    void addingUserWithLastNameLongerThan255CharactersShouldThrowAnException() {
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < 256; i++) {
            str.insert(i, "a");
        }

        User user = new User(99999L, "TestName", str.toString(), "TestPesel");

        //Then
        //When
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(user));
    }

    @Test
    void addingUserWithoutIdShouldThrowAnException() {
        //Given
        User user = new User();

        //When
        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(user));
    }

    @Test
    void addingUserWithPeselOfAlreadyExistingUsersPeselShouldThrowAnException() {
        //Given
        User user1 = new User(99999L, "TestName#1", "TestLastName#1", "TestPesel");
        testEntityManager.persistAndFlush(user1);
        User user2 = new User(100000L, "TestName#2", "TestLastName#2", "TestPesel");

        //When
        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(user2));
    }

    @Test
    void afterAddingUserRepositorySizeShouldIncreaseByOne() {
        //Given
        List<User> beforeAddingUserToRepository = userRepository.findAll();
        User user1 = new User(99999L, "TestName#1", "TestLastName#1", "TestPesel");
        testEntityManager.persistAndFlush(user1);
        //When
        List<User> afterAddingUserToRepository = userRepository.findAll();

        //Then
        assertAll(
                () -> assertThat(afterAddingUserToRepository, notNullValue()),
                () -> assertThat(afterAddingUserToRepository, hasSize(beforeAddingUserToRepository.size() + 1))
        );
    }

    @Test
    void findByLastNameShouldReturnListOfAllUsersWhenEmptyStringHasBeenProvided() {
        //Given
        List<User> users = userRepository.findByLastNameContainsIgnoreCase("");

        //When
        //Then
        assertThat(users, is(not(emptyCollectionOf(User.class))));
    }

    @Test
    void findByLastNameShouldReturnEmptyListForNonExistingLastNameForUsers() {
        //Given
        List<User> users = userRepository.findByLastNameContainsIgnoreCase("zxcvbnm,./';[]");

        //When
        //Then
        assertThat(users, is(empty()));
    }

    @Test
    void findByPeselShouldReturnUserForExistingPeselForUser() {
        //Given
        User createdUser = new User(99999L, "TestName", "TestLastName", "TestPesel");
        testEntityManager.persistAndFlush(createdUser);

        //Then
        Optional<User> foundUser = userRepository.findByPesel(createdUser.getPesel());

        if(foundUser.isEmpty()) {
            assert false;
        } else {
            assertAll(
                    () -> assertThat(foundUser.get(), sameInstance(createdUser)),
                    () -> assertThat(foundUser.get().getPesel(), equalTo(createdUser.getPesel())),
                    () -> assertThat(foundUser.get().getFirstName(), equalTo(createdUser.getFirstName())),
                    () -> assertThat(foundUser.get().getLastName(), equalTo(createdUser.getLastName())),
                    () -> assertThat(foundUser.get().getId(), equalTo(createdUser.getId()))
            );
        }
    }


}
