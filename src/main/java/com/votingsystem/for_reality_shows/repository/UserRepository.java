package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Make sure this import is present

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}