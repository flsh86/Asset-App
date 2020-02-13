package com.example.repositories;

import com.example.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset,Long> {
    List<Asset> findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(String name, String serialNumber);
    Optional<Asset> findBySerialNumberIgnoreCase(String serialNumber);

}
