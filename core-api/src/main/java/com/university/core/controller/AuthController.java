package com.university.core.controller;

import com.university.core.dto.request.LoginRequest;
import com.university.core.dto.request.RegisterUserRequest;
import com.university.core.dto.response.UserResponse;
import com.university.core.security.util.JwtUtil;
import com.university.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registrationUser) {

                userService.registerNewUser(registrationUser);
                return new ResponseEntity<>(
                        Map.of("message", "User registered successfully!"),
                        HttpStatus.CREATED);

        }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "id", user.getId(),
                        "firstName",user.getFirstName(),
                        "email", user.getEmail(),
                        "role", user.getRole().name(),
                        "message", "Login successful"
                )
        );
    }




}
