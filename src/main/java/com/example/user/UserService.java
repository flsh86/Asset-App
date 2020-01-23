package com.example.user;

import com.example.assignment.Assignment;
import com.example.exception.PeselConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findByLastName(String lastName) {
        return userRepository.findByLastNameContainsIgnoreCase(lastName)
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::toDTO).orElse(null);
    }

//    public UserDTO findByPesel(String pesel) {
//        Optional<User> user = userRepository.findByPesel(pesel);
//        return user.map(UserMapper::toDTO).orElse(null);
//    }
//
//    public void addUser(UserDTO userDTO) {
//        User user = UserMapper.toEntity(userDTO);
//        this.userRepository.save(user);
//    }

    public boolean save(UserDTO userDTO) {
        Optional<User> user = userRepository.findByPesel(userDTO.getPesel());
        if(user.isPresent()) {
            throw new PeselConflictException();
        }
        User userEntity = UserMapper.toEntity(userDTO);
        userRepository.save(userEntity);
        return true;
    }

    public boolean validPesel(UserDTO userDTO) {
        List<UserDTO> list = findAll();
        Predicate<String> peselPredicate = (value -> userDTO.getPesel().equalsIgnoreCase(value));

        if(list.isEmpty()) {
            return false;
        }

        return list.stream()
                .map(UserDTO::getPesel)
                .anyMatch(peselPredicate);
    }

    public List<UserAssignmentDTO> getUserAssignments(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            List<Assignment> assignments = user.get().getAssignments();
            return assignments.stream()
                    .map(UserAssignmentMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

}
