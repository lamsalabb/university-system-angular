package com.university.core.service;

import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.core.exception.EmailAlreadyExistsException;
import com.university.core.exception.EmailNotFoundException;
import com.university.core.exception.UserAlreadyExistsException;
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
    public User registerNewUser(User registrationUser){

        if (userRepository.findByEmail(registrationUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Registration failed: Email address already in use.");
        }

        String hashedPassword = passwordEncoder.encode(registrationUser.getPasswordHash());

        User user = User.builder()
                .email(registrationUser.getEmail())
                .passwordHash(hashedPassword)
                .role(registrationUser.getRole())
                .firstName(registrationUser.getFirstName())
                .lastName(registrationUser.getLastName())
                .isActive(true)
                .build();

        return userRepository.save(user);
    }


    //READ
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(int id){
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Email doesn't exist.")
        );
    }

    public List<User> findAllByRole(User.Role role){
        return userRepository.findByRole(role);
    }



    @Transactional//UPDATE
    public User updateUser(int id, User updatedUserDetails){

        User existingUser = findUserById(id);

        existingUser.setFirstName(updatedUserDetails.getFirstName());
        existingUser.setLastName(updatedUserDetails.getLastName());

        if (!existingUser.getEmail().equals(updatedUserDetails.getEmail())){
            userRepository.findByEmail(updatedUserDetails.getEmail()).ifPresent(
                    (userWithSameEmail) -> {
                        throw new EmailAlreadyExistsException("Email already exists. Please enter unique email.");
                    }
            );//check for unique email before updating
            existingUser.setEmail(updatedUserDetails.getEmail());

        }


        existingUser.setPasswordHash(passwordEncoder.encode(updatedUserDetails.getPasswordHash()));//separate password change logic later

        return userRepository.save(existingUser);
    }




    @Transactional//DELETE
    public void deleteUser(int id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


}
