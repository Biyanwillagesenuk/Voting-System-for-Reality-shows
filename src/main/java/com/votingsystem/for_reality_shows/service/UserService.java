package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.model.User;
import com.votingsystem.for_reality_shows.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setPhoneNum(userDetails.getPhoneNum());
        user.setRole(userDetails.getRole());
        user.setShowId(userDetails.getShowId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // NEW: Authentication logic migrated from Controller to Service Layer
    public User authenticateUser(String email, String password, String role) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found in our system. Please check or sign up."));

        if (!user.getRole().equalsIgnoreCase(role)) {
            throw new RuntimeException("Incorrect role selected for this account.");
        }

        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Incorrect password.");
        }

        return user;
    }
}
