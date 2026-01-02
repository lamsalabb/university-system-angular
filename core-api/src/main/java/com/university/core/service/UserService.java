package com.university.core.service;

import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.core.dto.mapper.UserMapper;
import com.university.core.dto.request.RegisterUserRequest;
import com.university.core.dto.request.UpdateUserRequest;
import com.university.core.dto.response.UserResponse;
import com.university.core.exception.EmailAlreadyExistsException;
import com.university.core.exception.EmailNotFoundException;
import com.university.core.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional//CREATE
    public UserResponse registerNewUser(RegisterUserRequest registrationUser){

        if (userRepository.findByEmail(registrationUser.getEmail()).isPresent()) {//checking unique entries
            throw new EmailAlreadyExistsException("Registration failed: Email address already in use.");
        }

        String hashedPassword = passwordEncoder.encode(registrationUser.getPassword());

        User user = User.builder()
                .email(registrationUser.getEmail())
                .passwordHash(hashedPassword)
                .role(registrationUser.getRole())
                .firstName(registrationUser.getFirstName())
                .lastName(registrationUser.getLastName())
                .isActive(true)
                .build();

        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }


    //READ
    public List<UserResponse> findAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
        return UserMapper.toResponse(user);
    }


    public UserResponse findUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Email doesn't exist.")
        );
        return UserMapper.toResponse(user);
    }


    public List<UserResponse> findAllByRole(User.Role role){

        return userRepository.findByRole(role)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }



    @Transactional//UPDATE
    public UserResponse updateUser(int id, UpdateUserRequest updatedUserDetails) {

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

        User saved = userRepository.save(existingUser);
        return UserMapper.toResponse(saved);
    }




    @Transactional//DELETE
    public void deleteUser(int id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Invalid email or password")
        );

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new EmailNotFoundException("Invalid email or password");
        }

        return UserMapper.toResponse(user);
    }



}
