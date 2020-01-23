package com.example.user;

public class UserMapper {

    static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPesel(user.getPesel());
        return userDTO;
    }

    static User toEntity(UserDTO userDTO) {
        User entity = new User();
        entity.setId(userDTO.getId());
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setPesel(userDTO.getPesel());
        return entity;
    }
}
