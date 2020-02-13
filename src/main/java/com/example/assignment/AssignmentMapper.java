package com.example.assignment;

import com.example.asset.Asset;
import com.example.repositories.AssetRepository;
import com.example.exception.NotFoundException;
import com.example.user.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = assignment.getUser();
        Asset asset = assignment.getAsset();

        return new AssignmentDTO(
                assignment.getId(),
                assignment.getStart(),
                assignment.getEnd(),
                asset.getId(),
                user.getId()
        );
    }

    public Assignment toEntity(AssignmentDTO dto) throws NotFoundException {
        Assignment entity = new Assignment(
                dto.getId(),
                dto.getStart(),
                dto.getEnd()
        );

        Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
        Optional<User> user = userRepository.findById(dto.getUserId());

        entity.setId(dto.getId());
        entity.setStart(dto.getStart());
        entity.setEnd(dto.getEnd());

        if(asset.isPresent()) {
            entity.setAsset(asset.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset was not found");
        }

        if(user.isPresent()) {
            entity.setUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }

        return entity;
    }
}
