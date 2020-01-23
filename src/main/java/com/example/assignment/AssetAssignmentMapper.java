package com.example.assignment;

import com.example.user.User;

public class AssetAssignmentMapper {

    public static AssetAssignmentDTO toDTO(Assignment assignment) {
        AssetAssignmentDTO dto = new AssetAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());

        User user = assignment.getUser();
        dto.setUserId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPesel(user.getPesel());

        return dto;
    }
}
