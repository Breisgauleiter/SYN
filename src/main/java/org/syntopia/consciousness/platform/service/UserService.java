package org.syntopia.consciousness.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;
import org.syntopia.consciousness.platform.repository.tao.TAOUserRepository;

import java.util.Optional;

/**
 * User Service for Sacred Contributing System
 * 
 * Service layer for user management operations required by the
 * Sacred Contributing System. Provides convenient methods for
 * user lookup and consciousness level management.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private TAOUserRepository userRepository;

    /**
     * Find user by email
     * 
     * @param email User email address
     * @return User entity
     * @throws RuntimeException if user not found
     */
    public SyntopiaUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    /**
     * Find user by ID
     * 
     * @param userId User identifier
     * @return User entity
     * @throws RuntimeException if user not found
     */
    public SyntopiaUser findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    /**
     * Find user by email (optional)
     * 
     * @param email User email address
     * @return Optional user entity
     */
    public Optional<SyntopiaUser> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by ID (optional)
     * 
     * @param userId User identifier
     * @return Optional user entity
     */
    public Optional<SyntopiaUser> findByIdOptional(String userId) {
        return userRepository.findById(userId);
    }

    /**
     * Save user entity
     * 
     * @param user User to save
     * @return Saved user entity
     */
    public SyntopiaUser save(SyntopiaUser user) {
        return userRepository.save(user);
    }

    /**
     * Get user's current Sacred Consciousness Level
     * 
     * @param user User entity
     * @return Current SCL level (1-25)
     */
    public Integer getCurrentSCLLevel(SyntopiaUser user) {
        if (user.getCurrentLevel() != null) {
            return user.getCurrentLevel().getLevel();
        }
        return 1; // Default to SCL 1 - Sacred Seeker
    }

    /**
     * Update user's Sacred Consciousness Level
     * 
     * @param user User entity
     * @param newLevel New SCL level
     * @return Updated user entity
     */
    public SyntopiaUser updateSCLLevel(SyntopiaUser user, Integer newLevel) {
        if (user.getCurrentLevel() != null) {
            user.getCurrentLevel().setLevel(newLevel);
        }
        return save(user);
    }

    /**
     * Check if user exists by email
     * 
     * @param email Email to check
     * @return true if user exists
     */
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Check if user exists by username
     * 
     * @param username Username to check
     * @return true if user exists
     */
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
