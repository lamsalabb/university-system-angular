package com.university.core.controller;

import com.university.common.entity.User;
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
public class RegistrationController {

    private final UserService userService;


    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")//checking for unique entries
    public ResponseEntity<?> registerUser(@RequestBody User registrationUser) {

                userService.registerNewUser(registrationUser);
                return new ResponseEntity<>(
                        Map.of("message", "User registered successfully!"),
                        HttpStatus.CREATED);

        }
}
