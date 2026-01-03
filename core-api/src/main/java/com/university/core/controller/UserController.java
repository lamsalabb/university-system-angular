package com.university.core.controller;

import com.university.common.entity.User;
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

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.findAllUsers()
                .stream()
                .map(UserMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<UserResponse>> getAllUsersByRole(@RequestParam User.Role role) {

        List<UserResponse> users = userService.findAllByRole(role)
                .stream()
                .map(UserMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {

        UserResponse user =
                UserMapper.toResponse(userService.findUserById(id));

        return ResponseEntity.ok(user);
    }

    @PostMapping // Admin creation
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody RegisterUserRequest newUser) {

        UserResponse user =
                UserMapper.toResponse(userService.registerNewUser(newUser));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UpdateUserRequest userDetails) {

        UserResponse updatedUser =
                UserMapper.toResponse(userService.updateUser(id, userDetails));

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
