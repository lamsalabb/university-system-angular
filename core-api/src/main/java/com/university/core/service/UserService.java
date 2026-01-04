package com.university.core.service;

import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.core.dto.request.RegisterUserRequest;
import com.university.core.dto.request.UpdateUserRequest;
import com.university.core.exception.EmailAlreadyExistsException;
import com.university.core.exception.EmailNotFoundException;
import com.university.core.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    //READ
//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
    }


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Email doesn't exist.")
        );

    }


    public List<User> findAllByRole(User.Role role) {
        return userRepository.findByRole(role);
    }


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


}
