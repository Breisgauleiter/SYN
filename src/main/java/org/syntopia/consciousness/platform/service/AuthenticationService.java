package org.syntopia.consciousness.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;
import org.syntopia.consciousness.platform.dto.auth.*;
import org.syntopia.consciousness.platform.repository.tao.TAOUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sacred Authentication Service
 * Handles user registration, login, and token management
 */
@Service
public class AuthenticationService {

    @Autowired
    private TAOUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    // In-memory token blacklist (for MVP - use Redis in production)
    private final ConcurrentHashMap<String, LocalDateTime> tokenBlacklist = new ConcurrentHashMap<>();

    /**
     * Register new sacred user
     */
    public AuthResponseDto registerUser(RegisterRequestDto registerRequest) {
        // Validate terms acceptance
        if (!registerRequest.isAcceptTerms() || !registerRequest.isAcceptPrivacyPolicy()) {
            throw new RuntimeException("Terms and Privacy Policy must be accepted");
        }

        // Check if user already exists
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        // Create new user
        SyntopiaUser user = new SyntopiaUser();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setConsciousnessLevel(registerRequest.getInitialConsciousnessLevel());
        user.setPersonalVision(registerRequest.getPersonalVision());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastActiveAt(LocalDateTime.now());

        // Save user
        SyntopiaUser savedUser = userRepository.save(user);

        // Generate tokens
        TokenResponseDto tokens = jwtTokenService.generateTokens(savedUser);

        // Convert to DTOs
        UserDto userDto = convertToUserDto(savedUser);
        
        return new AuthResponseDto(userDto, tokens, "Sacred registration successful! Welcome to the consciousness network.");
    }

    /**
     * Authenticate user login
     */
    public AuthResponseDto authenticateUser(LoginRequestDto loginRequest) {
        // Find user by email
        SyntopiaUser user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Update last active
        user.setLastActiveAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate tokens
        TokenResponseDto tokens = jwtTokenService.generateTokens(user);

        // Convert to DTO
        UserDto userDto = convertToUserDto(user);

        return new AuthResponseDto(userDto, tokens, "Sacred login successful! Welcome back to the consciousness network.");
    }

    /**
     * Refresh access token
     */
    public TokenResponseDto refreshToken(String refreshToken) {
        if (!jwtTokenService.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = jwtTokenService.getUserIdFromToken(refreshToken);
        SyntopiaUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtTokenService.generateTokens(user);
    }

    /**
     * Verify token validity
     */
    public boolean verifyToken(String token) {
        // Check blacklist first
        if (tokenBlacklist.containsKey(token)) {
            return false;
        }

        return jwtTokenService.validateToken(token);
    }

    /**
     * Logout user (blacklist token)
     */
    public void logoutUser(String token) {
        // Add token to blacklist
        tokenBlacklist.put(token, LocalDateTime.now());
        
        // Clean up old blacklisted tokens (older than 24 hours)
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        tokenBlacklist.entrySet().removeIf(entry -> entry.getValue().isBefore(cutoff));
    }

    /**
     * Update user profile
     */
    public UserDto updateUserProfile(String token, ProfileUpdateDto profileUpdate) {
        if (!verifyToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        String userId = jwtTokenService.getUserIdFromToken(token);
        SyntopiaUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields
        if (profileUpdate.getFirstName() != null) {
            user.setFirstName(profileUpdate.getFirstName());
        }
        if (profileUpdate.getLastName() != null) {
            user.setLastName(profileUpdate.getLastName());
        }
        if (profileUpdate.getPersonalVision() != null) {
            user.setPersonalVision(profileUpdate.getPersonalVision());
        }

        user.setUpdatedAt(LocalDateTime.now());
        SyntopiaUser updatedUser = userRepository.save(user);

        return convertToUserDto(updatedUser);
    }

    /**
     * Request password reset (MVP implementation)
     */
    public void requestPasswordReset(String email) {
        // For MVP, just log the request
        // In production, send email with reset token
        System.out.println("🔑 Password reset requested for: " + email);
    }

    /**
     * Reset password (MVP implementation)
     */
    public void resetPassword(String token, String newPassword) {
        // For MVP, basic implementation
        // In production, validate reset token properly
        throw new RuntimeException("Password reset not fully implemented in MVP");
    }

    /**
     * Convert User entity to UserDto
     */
    private UserDto convertToUserDto(SyntopiaUser user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setConsciousnessLevel(user.getConsciousnessLevel());
        dto.setPersonalVision(user.getPersonalVision());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setLastActiveAt(user.getLastActiveAt());
        return dto;
    }
}
