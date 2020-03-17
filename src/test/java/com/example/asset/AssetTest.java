package com.example.asset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    @Test
    void createAssetWithInvalidIdShouldThrowAnException() {
        //Given
        long exceed = Long.MAX_VALUE;
        //When
        //Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Asset(exceed + 1, "TestName", "TestDescription", "TestSerialNumber")),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    Asset asset = new Asset();
                    asset.setId(exceed + 1);
                })
        );
    }
}