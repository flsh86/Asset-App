package com.example.assignment;

import com.example.asset.Asset;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    void createAssignmentWithInvalidIdShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;

        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Assignment(exceed + 1, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    Assignment assignment = new Assignment();
                    assignment.setId(exceed + 1);
                })
        );
    }

    @Test
    void createdAssignmentWithStartBeginsAfterEndStartShouldBeSetToNull() {

    }

    @Test
    void setEnd() {
    }
}