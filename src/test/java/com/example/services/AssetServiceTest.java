package com.example.services;

import com.example.repositories.AssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;
    

    @Test
    void findAll() {
    }

    @Test
    void findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void getAssetAssignment() {
    }
}