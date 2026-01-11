package com.university.core.service;

import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.common.dto.request.RegisterUserRequest;
import com.university.common.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private RegisterUserRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterUserRequest();
        request.setEmail("test@uni.edu");
        request.setPassword("password123");
        request.setFirstName("Abiral");
        request.setLastName("Lamsal");
        request.setRole(User.Role.STUDENT);
    }

    @Test
    void registerNewUser_success() {
        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("hashedPassword");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.registerNewUser(request);

        assertNotNull(savedUser);
        assertEquals(request.getEmail(), savedUser.getEmail());
        assertEquals("hashedPassword", savedUser.getPasswordHash());
        assertTrue(savedUser.getIsActive());

        verify(userRepository).save(any(User.class));
        verify(emailService)
                .sendUserCredentials(request.getEmail(), request.getPassword());
    }

    @Test
    void registerNewUser_emailAlreadyExists() {
        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.registerNewUser(request));

        verify(userRepository, never()).save(any());
        verify(emailService, never()).sendUserCredentials(any(), any());
    }
}
