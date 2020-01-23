package com.example.asset;

import com.example.assignment.AssetAssignmentDTO;
import com.example.assignment.AssetAssignmentMapper;
import com.example.exception.SerialNumberConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
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
        return assetRepository.findAll()
                .stream()
                .map(assetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssetDTO> findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(String name, String serialNumber) {
        return assetRepository.findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(name, serialNumber)
                .stream()
                .map(assetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean validSerialNumber(AssetDTO assetDTO) {
        List<AssetDTO> list = findAll();

        Predicate<String> serialNumberPredicate = (value -> assetDTO.getSerialNumber().equalsIgnoreCase(value));

        if (list.isEmpty()) {
            return false;
        }

        return list.stream()
                .map(AssetDTO::getSerialNumber)
                .anyMatch(serialNumberPredicate);
    }

    public boolean save(AssetDTO assetDTO) {
        Optional<Asset> asset = assetRepository.findBySerialNumberIgnoreCase(assetDTO.getSerialNumber());
        if (asset.isPresent()) {
            throw new SerialNumberConflictException();
        }

        Asset entity = assetMapper.toEntity(assetDTO);
        assetRepository.save(entity);
        return true;
    }

    public AssetDTO findById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        return asset.map(value -> assetMapper.toDTO(value)).orElse(null);
    }

    public List<AssetAssignmentDTO> getAssetAssignment(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        return asset.map(value -> value.getAssignments()
                .stream()
                .map(AssetAssignmentMapper::toDTO)
                .collect(Collectors.toList())).orElse(null);
    }

}
