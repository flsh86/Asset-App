package com.example.user;

import com.example.exception.IllegalIdException;
import com.example.exception.PeselConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required = false) String lastName) {
        if(lastName != null) {
            List<UserDTO> lastNameList = this.userService.findByLastName(lastName);
            return ResponseEntity.ok(lastNameList);
        }

        List<UserDTO> users = this.userService.findAll();
        if(!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        if(userDTO.getId() != null) {
            throw new IllegalIdException();
        }
        this.userService.save(userDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(userDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUser(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik o takim ID nie istnieje");
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        if(!userDTO.getId().equals(id) || userDTO.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        }
        if(userService.validPesel(userDTO)) {
            throw new PeselConflictException();
        }

        UserDTO user = userService.findById(id);
        user.setPesel(userDTO.getPesel());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        userService.save(user);
       return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{id}/assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAssignmentDTO>> getUserAssignments(@PathVariable Long id) {
        List<UserAssignmentDTO> list = userService.getUserAssignments(id);
        if(list == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

//    private boolean peselChecker(UserDTO userDTO) {
//        List<UserDTO> list = userService.findAll();
//        Predicate<String> peselPredicate = (value -> userDTO.getPesel().equalsIgnoreCase(value));
//
//        if(list.isEmpty()) {
//            return false;
//        }
//
//        return list.stream()
//                .map(UserDTO::getPesel)
//                .anyMatch(peselPredicate);
//    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
//        if(userDTO.getId() != null) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST)
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ma ustawione id");
//            throw new SetIdException();
//        } else if(userService.findByPesel(userDTO.getPesel()) != null) {
////            return ResponseEntity.status(HttpStatus.CONFLICT).body("ten sam pesel");
////            throw new ResponseStatusException(HttpStatus.CONFLICT);
//            throw new PeselConflictException();
//        } else {
//            this.userService.addUser(userDTO);
//            URI location = ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .path("/{id}")
//                    .buildAndExpand(userDTO.getId())
//                    .toUri();
//            return ResponseEntity.created(location).body(userDTO);
////            return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
//        }
//    }

//    @GetMapping
//    public ResponseEntity<List<UserDTO>> findAll() {
//        List<UserDTO> users = this.userService.findAll();
//        if(!users.isEmpty()) {
//            return ResponseEntity.ok(users);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping(params = "lastName")
//    public ResponseEntity<List<UserDTO>> findByLastName(@RequestParam String lastName) {
//        List<UserDTO> users = this.userService.findByLastName(lastName);
//        if(!users.isEmpty()) {
//            return ResponseEntity.ok(users);
//        }
//        return ResponseEntity.notFound().build();
//    }

//    private boolean checkUser(UserDTO userDTO) {
//        UserDTO user = this.userService.findBy
//    }

}
