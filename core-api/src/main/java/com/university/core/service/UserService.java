package com.university.core.service;

import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.common.dto.request.RegisterUserRequest;
import com.university.common.dto.request.UpdateUserRequest;
import com.university.common.exception.EmailAlreadyExistsException;
import com.university.common.exception.EmailNotFoundException;
import com.university.common.exception.InvalidPasswordException;
import com.university.common.exception.UserNotFoundException;
import com.university.common.annotation.SelfOnly;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Transactional
    public User registerNewUser(RegisterUserRequest registrationUser) {

        if (userRepository.findByEmail(registrationUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "Registration failed: Email address already in use."
            );
        }

        String rawPassword = registrationUser.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(registrationUser.getEmail())
                .passwordHash(hashedPassword)
                .role(registrationUser.getRole())
                .firstName(registrationUser.getFirstName())
                .lastName(registrationUser.getLastName())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        emailService.sendUserCredentials(savedUser.getEmail(), rawPassword
        );

        return savedUser;
    }



    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @SelfOnly
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
    }


    public List<User> findAllByRole(User.Role role) {
        return userRepository.findByRole(role);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional//UPDATE
    public User updateUser(int id, UpdateUserRequest updatedUserDetails) {

        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );

        if (updatedUserDetails.getFirstName() != null) {
            existingUser.setFirstName(updatedUserDetails.getFirstName());
        }
        if (updatedUserDetails.getLastName() != null) {
            existingUser.setLastName(updatedUserDetails.getLastName());
        }

        if (updatedUserDetails.getEmail() != null
                && !updatedUserDetails.getEmail().equals(existingUser.getEmail())) {

            userRepository.findByEmail(updatedUserDetails.getEmail()).ifPresent(u -> {
                throw new EmailAlreadyExistsException("Email already exists. Please enter unique email.");
            });

            existingUser.setEmail(updatedUserDetails.getEmail());
        }

        // only change password if provided and not blank
        if (updatedUserDetails.getPassword() != null && !updatedUserDetails.getPassword().isBlank()) {
            existingUser.setPasswordHash(passwordEncoder.encode(updatedUserDetails.getPassword()));
        }

        //only for admins
        if (updatedUserDetails.getIsActive() != null) {
            existingUser.setIsActive(updatedUserDetails.getIsActive());
        }

        return userRepository.save(existingUser);
    }


    @Transactional//DELETE
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Invalid email or password")
        );

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new EmailNotFoundException("Invalid email or password");
        }

        return user;
    }

    @SelfOnly
    @Transactional
    public void changePasswordUser(int id, String currentPassword, String newPassword) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
    }


}
