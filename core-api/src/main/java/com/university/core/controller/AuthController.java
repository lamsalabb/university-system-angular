package com.university.core.controller;

import com.university.common.dto.mapper.UserMapper;
import com.university.common.dto.response.UserResponse;
import com.university.core.dto.request.LoginRequest;
import com.university.core.security.util.JwtUtil;
import com.university.core.service.UserService;
import jakarta.validation.Valid;
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


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse user = UserMapper.toResponse(userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword()));

        String token = jwtUtil.generateToken(user.id(), user.role().name());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "id", user.id(),
                        "firstName", user.firstName(),
                        "email", user.email(),
                        "role", user.role().name(),
                        "message", "Login successful"
                )
        );
    }


}
