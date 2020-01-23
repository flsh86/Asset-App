package com.example.user;

import com.example.asset.Asset;
import com.example.assignment.Assignment;

public class UserAssignmentMapper {
    public static UserAssignmentDTO toDTO(Assignment assignment) {
        UserAssignmentDTO dto = new UserAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());

        Asset asset = assignment.getAsset();
        dto.setAssetId(asset.getId());
        dto.setAssetName(asset.getName());
        dto.setAssetSerialNumber(asset.getSerialNumber());

        return dto;
    }

}
