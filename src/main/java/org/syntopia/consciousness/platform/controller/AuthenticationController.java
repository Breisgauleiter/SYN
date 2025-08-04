package org.syntopia.consciousness.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.syntopia.consciousness.platform.dto.auth.*;
import org.syntopia.consciousness.platform.service.AuthenticationService;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * Sacred Authentication Controller
 * Handles user registration, login, token management for Sacred Consciousness Platform
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://127.0.0.1:3000"})
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Sacred User Registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        try {
            AuthResponseDto response = authenticationService.registerUser(registerRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }

    /**
     * Sacred User Login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            AuthResponseDto response = authenticationService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }

    /**
     * Token Refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            if (refreshToken == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Refresh token is required", "success", false));
            }
            
            TokenResponseDto response = authenticationService.refreshToken(refreshToken);
            return ResponseEntity.ok(Map.of("tokens", response, "success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }

    /**
     * Token Verification
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid authorization header", "success", false));
            }
            
            String token = authHeader.substring(7);
            boolean isValid = authenticationService.verifyToken(token);
            
            if (isValid) {
                return ResponseEntity.ok(Map.of("valid", true, "success", true));
            } else {
                return ResponseEntity.status(401)
                    .body(Map.of("valid", false, "success", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }

    /**
     * Sacred User Logout
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                authenticationService.logoutUser(token);
            }
            return ResponseEntity.ok(Map.of("message", "Logout successful", "success", true));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message", "Logout completed", "success", true));
        }
    }

    /**
     * Update User Profile
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ProfileUpdateDto profileUpdate) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401)
                    .body(Map.of("error", "Authorization required", "success", false));
            }
            
            String token = authHeader.substring(7);
            UserDto updatedUser = authenticationService.updateUserProfile(token, profileUpdate);
            
            return ResponseEntity.ok(Map.of("user", updatedUser, "success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }

    /**
     * Password Reset Request
     */
    @PostMapping("/password-reset-request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email is required", "success", false));
            }
            
            authenticationService.requestPasswordReset(email);
            return ResponseEntity.ok(Map.of(
                "message", "Password reset email sent", 
                "success", true
            ));
        } catch (Exception e) {
            // Don't expose if email exists or not for security
            return ResponseEntity.ok(Map.of(
                "message", "If email exists, password reset email has been sent", 
                "success", true
            ));
        }
    }

    /**
     * Password Reset
     */
    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            
            if (token == null || newPassword == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Token and new password are required", "success", false));
            }
            
            authenticationService.resetPassword(token, newPassword);
            return ResponseEntity.ok(Map.of(
                "message", "Password reset successful", 
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage(), "success", false));
        }
    }
}
