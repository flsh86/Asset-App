package com.example.user;

import com.example.asset.Asset;
import com.example.assignment.Assignment;

public class UserAssignmentMapper {
    public static UserAssignmentDTO toDTO(Assignment assignment) {
        Asset asset = assignment.getAsset();

        return new UserAssignmentDTO(
                assignment.getId(),
                assignment.getStart(),
                assignment.getEnd(),
                asset.getId(),
                asset.getName(),
                asset.getSerialNumber()
        );
    }

}
