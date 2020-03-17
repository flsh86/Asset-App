package com.example.repositories;

import com.example.category.Category;
import com.example.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createdCategoryShouldBeAddedToRepository() {
        //Given
        Category category = new Category(99999L, "TestName", "TestDescription");
        testEntityManager.persistAndFlush(category);

        //When
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        //Then
        if(foundCategory.isEmpty()) {
            assert false;
        } else {
            assertAll(
                    () -> assertThat(category.getId(), equalTo(foundCategory.get().getId())),
                    () -> assertThat(category.getName(), equalTo(foundCategory.get().getName())),
                    () -> assertThat(category.getDescription(), equalTo(foundCategory.get().getDescription()))
            );
        }
    }

    @Test
    void addingCategoryWithAlreadyExistingIdShouldThrowAnException() {
        //Given
        Category category1 = new Category(99999L, "TestCategory#1", "TestCategoryDescription#1");
        Category category2 = new Category(99999L, "TestCategory#2", "TestCategoryDescription#2");

        //When
        testEntityManager.persistAndFlush(category1);

        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(category2));
    }

    @Test
    void addingCategoryWithNameOfAlreadyExistingCategoryShouldThrowAnException() {
        //Given
        Category category1 = new Category(99999L, "TestCategory#1", "TestCategoryDescription#1");
        Category category2 = new Category(100000L, "TestCategory#1", "TestCategoryDescription#2");

        //When
        testEntityManager.persistAndFlush(category1);

        //Then
        assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(category2));
    }

    @Test
    void findByNameShouldNotReturnNotExistedCategory() {
        //Given
        Optional<Category> category = categoryRepository.findByNameIgnoreCase("@!#$T%$Y^%&");

        //When
        //Then
        if(category.isPresent()) {
            assert false;
        } else {
            assertThrows(NoSuchElementException.class, () -> category.get());
        }
    }

    @Test
    void findByNameShouldReturnExistingCategory() {
        //Given
        Category category = new Category(99999L, "TestCategoryName", "TestCategoryDescription");

        //When
        Optional<Category> foundCategory = categoryRepository.findByNameIgnoreCase("TestCategoryName");

        //Then
        if(foundCategory.isEmpty()) {
            assert false;
        } else {
            assertAll(
                    () -> assertThat(category.getId(), equalTo(foundCategory.get().getId())),
                    () -> assertThat(category.getName(), equalTo(foundCategory.get().getName())),
                    () -> assertThat(category.getDescription(), equalTo(foundCategory.get().getDescription()))
            );
        }
    }

    @Test
    void findByNameContainsShouldNotReturnAnyCategory() {
        //Given
        String noCategoryString = "!@#$%^&";

        //When
        List<Category> categories = categoryRepository.findByNameContainsIgnoreCase(noCategoryString);

        //Then
        assertAll(
                () -> assertThat(categories, hasSize(0)),
                () -> assertThat(categories, is(emptyCollectionOf(Category.class)))
        );
    }

    @Test
    void findByNameContainsShouldReturnCategory() {
        //Given
        Category category = new Category(99999L, "TestName", "TestDescription");
        testEntityManager.persistAndFlush(category);

        //When
        List<Category> categories = categoryRepository.findByNameContainsIgnoreCase("Test");

        //Then
        assertAll(
                () -> assertThat(categories, hasSize(1)),
                () -> assertThat(categories, is(not(emptyCollectionOf(Category.class))))
        );
    }

}
