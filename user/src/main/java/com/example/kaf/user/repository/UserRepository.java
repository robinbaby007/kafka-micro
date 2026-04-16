package com.example.kaf.user.repository;

import com.example.kaf.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 * JpaRepository provides CRUD methods (save, findById, delete, etc.) automatically.
 * We define custom methods below for queries by email/username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email - returns Optional to handle "not found" gracefully
    Optional<User> findByEmail(String email);

}
