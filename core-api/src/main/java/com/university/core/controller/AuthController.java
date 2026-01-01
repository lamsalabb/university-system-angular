package com.university.core.controller;

import com.university.common.entity.User;
import com.university.core.security.util.JwtUtil;
import com.university.core.service.UserService;
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

    @PostMapping("/register")//checking for unique entries
    public ResponseEntity<?> registerUser(@RequestBody User registrationUser) {

                userService.registerNewUser(registrationUser);
                return new ResponseEntity<>(
                        Map.of("message", "User registered successfully!"),
                        HttpStatus.CREATED);

        }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String,String> loginRequest){
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userService.authenticate(email,password);

        String token = jwtUtil.generateToken(user.getId(), String.valueOf(user.getRole()));


        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "role", user.getRole().toString(),
                        "message", "Login successful"
                )
        );
    }



}
