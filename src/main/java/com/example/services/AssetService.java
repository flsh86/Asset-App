package com.example.services;

import com.example.asset.*;
import com.example.mapper.AssetAssignmentMapper;
import com.example.mapper.AssetMapper;
import com.example.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {
    private AssetRepository assetRepository;
    private AssetMapper assetMapper;

    @Autowired
    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    public List<AssetDTO> findAll() {
        List<Asset> assets = assetRepository.findAll();

        if(assets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return assets
                .stream()
                .map(assetMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<AssetDTO> findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(String name, String serialNumber) {
        List<Asset> assets = assetRepository.findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(name, serialNumber);

        if(assets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assets
                .stream()
                .map(assetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void update(Long id, AssetDTO assetDTO) {
        if(assetDTO.getId() == null || !assetDTO.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        }

        if(validSerialNumber(assetDTO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Serial numbers must be unique");
        }

        save(assetDTO);
    }


    public void save(AssetDTO assetDTO) {
        Optional<Asset> asset = assetRepository.findBySerialNumberIgnoreCase(assetDTO.getSerialNumber());
        if (asset.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Asset with this serialNumber already exist");
        }

        if(assetDTO.getId() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid id");
        }

        Asset entity = assetMapper.toEntity(assetDTO);
        assetRepository.save(entity);
    }

    public AssetDTO findById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        return asset
                .map(value -> assetMapper.toDTO(value))
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

    }

    public List<AssetAssignmentDTO> getAssetAssignment(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);

        if(asset.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return asset.map(value -> value.getAssignments()
                .stream()
                .map(AssetAssignmentMapper::toDTO)
                .collect(Collectors.toList())).get();
    }


    private boolean validSerialNumber(AssetDTO assetDTO) {
        return findAll().stream()
                .map(AssetDTO::getSerialNumber)
                .anyMatch(value -> assetDTO.getSerialNumber().equalsIgnoreCase(value));
    }

}
