package com.example.mapper;

import com.example.asset.Asset;
import com.example.assignment.Assignment;
import com.example.user.UserAssignmentDTO;

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
