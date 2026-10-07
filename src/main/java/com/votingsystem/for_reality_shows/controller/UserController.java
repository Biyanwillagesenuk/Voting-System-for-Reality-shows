package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.User;
import com.votingsystem.for_reality_shows.repository.UserRepository;
import com.votingsystem.for_reality_shows.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPasswordHash(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");
            String role = loginData.get("role");

            User authenticatedUser = userService.authenticateUser(email, password, role);
            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email, password, or role"));
            }


            Map<String, Object> response = new HashMap<>();
            response.put("id", authenticatedUser.getUId());
            response.put("name", authenticatedUser.getName());
            response.put("email", authenticatedUser.getEmail());
            response.put("role", authenticatedUser.getRole());
            response.put("phoneNum", authenticatedUser.getPhoneNum());
            response.put("showId", authenticatedUser.getShowId());

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", ex.getMessage()));
        }
    }

    @PutMapping("/{id}/update-profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User updatedDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedDetails.getName());
            user.setPhoneNum(updatedDetails.getPhoneNum());
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully!", "user", user));
        }
        return ResponseEntity.notFound().build();
    }
}