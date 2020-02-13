package com.example.asset;

import com.example.asset.AssetAssignmentDTO;
import com.example.assignment.Assignment;
import com.example.user.User;

public class AssetAssignmentMapper {

    public static AssetAssignmentDTO toDTO(Assignment assignment) {
        User user = assignment.getUser();

        return new AssetAssignmentDTO(
                assignment.getId(),
                assignment.getStart(),
                assignment.getEnd(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPesel()
        );
    }
}
