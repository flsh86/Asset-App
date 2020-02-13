package com.example.services;

import com.example.assignment.Assignment;
import com.example.repositories.UserRepository;
import com.example.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return users
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findByLastName(String lastName) {
        List<User> users = userRepository.findByLastNameContainsIgnoreCase(lastName);

        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return users
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return user.map(UserMapper::toDTO).orElse(null);
    }


    public void save(UserDTO userDTO) {
        Optional<User> user = userRepository.findByPesel(userDTO.getPesel());

        if(user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with that pesel number already exist");
        }

        User userEntity = UserMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }


    public List<UserAssignmentDTO> getUserAssignments(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            List<Assignment> assignments = user.get().getAssignments();

            if(assignments.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return assignments.stream()
                    .map(UserAssignmentMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateUser(Long id, UserDTO userDTO) {
        if (!userDTO.getId().equals(id) || userDTO.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        }

        Optional<User> user = userRepository.findByPesel(userDTO.getPesel());
        if(!user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this pesel already exist");
        }

        save(userDTO);
    }

}
