package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    private String passwordHash;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNum;

    @NotBlank(message = "Role is required")
    private String role; // ADMIN, VOTER, CONTESTANT, JUDGE

    private String showId; // Required for ADMIN, CONTESTANT, JUDGE; optional for VOTER

    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

    private User(Builder builder) {
        this.uId = builder.uId;
        this.name = builder.name;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.phoneNum = builder.phoneNum;
        this.role = builder.role;
        this.showId = builder.showId;
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
    }

    // --- BUILDER PATTERN (Creational) ---
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long uId;
        private String name;
        private String email;
        private String passwordHash;
        private String phoneNum;
        private String role;
        private String showId;
        private LocalDateTime createdAt;

        public Builder uId(Long uId) { this.uId = uId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder phoneNum(String phoneNum) { this.phoneNum = phoneNum; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder showId(String showId) { this.showId = showId; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public User build() {
            return new User(this);
        }
    }

    // Getters and Setters
    public Long getUId() { return uId; }
    public void setUId(Long uId) { this.uId = uId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
