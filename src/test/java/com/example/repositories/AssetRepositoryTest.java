package com.example.repositories;

import com.example.asset.Asset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AssetRepositoryTest {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase() {
        //given
        Optional<Asset> asset = assetRepository.findById(1L);
        List<Asset> assets = new ArrayList<>();

        //when
        if(asset.isEmpty()) {
            assert false;
        } else {
            assets = assetRepository.findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(asset.get().getSerialNumber(), asset.get().getSerialNumber());
        }

        //then
        assertThat(assets, hasSize(1));

    }

    @Test
    void findBySerialNumberIgnoreCase() {
    }


}