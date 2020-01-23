package com.example.assignment;

import com.example.asset.Asset;
import com.example.asset.AssetRepository;
import com.example.exception.NotFoundException;
import com.example.user.User;
import com.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentMapper {
    private AssetRepository assetRepository;
    private UserRepository userRepository;

    @Autowired
    public AssignmentMapper(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public static AssignmentDTO toDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        User user = assignment.getUser();
        Asset asset = assignment.getAsset();

        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());
        dto.setAssetId(asset.getId());
        dto.setAssetId(user.getId());

        return dto;
    }

    public Assignment toEntity(AssignmentDTO dto) throws NotFoundException {
        Assignment entity = new Assignment();
        Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
        Optional<User> user = userRepository.findById(dto.getUserId());

        entity.setId(dto.getId());
        entity.setStart(dto.getStart());
        entity.setEnd(dto.getEnd());

        if(asset.isPresent()) {
            entity.setAsset(asset.get());
        } else {
            throw new NotFoundException("Asset was not found");
        }

        if(user.isPresent()) {
            entity.setUser(user.get());
        } else {
            throw new NotFoundException("User was not found");
        }

        return entity;
    }
}
