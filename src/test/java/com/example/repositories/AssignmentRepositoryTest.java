package com.example.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AssignmentRepositoryTest {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

}