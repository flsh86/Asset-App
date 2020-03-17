package com.example.asset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetDTOTest {

    @Test
    void createAssetWithInvalidIdShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;
        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new AssetDTO(exceed + 1, "TestName", "TestDescription", "TestSerialNumber")),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    AssetDTO assetDTO = new AssetDTO();
                    assetDTO.setId(exceed + 1);
                })
        );
    }
}