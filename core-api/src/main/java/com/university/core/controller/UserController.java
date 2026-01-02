package com.university.core.controller;

import com.university.core.dto.mapper.UserMapper;
import com.university.core.dto.request.RegisterUserRequest;
import com.university.core.dto.request.UpdateUserRequest;
import com.university.core.dto.response.UserResponse;
import com.university.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.findAllUsers()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
            UserResponse user = UserMapper.toResponse(userService.findUserById(id));
            return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping//Admin creation
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterUserRequest newUser){
            UserResponse user = UserMapper.toResponse(userService.registerNewUser(newUser));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @Valid @RequestBody UpdateUserRequest userDetails){
            UserResponse updatedUser = UserMapper.toResponse(userService.updateUser(id, userDetails));
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
            userService.deleteUser(id);
            return new ResponseEntity<>(
                    Map.of("message","User deleted Successfully."),
                    HttpStatus.OK
            );
    }



}
