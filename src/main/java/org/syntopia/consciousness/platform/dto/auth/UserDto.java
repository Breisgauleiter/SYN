package org.syntopia.consciousness.platform.dto.auth;

import java.time.LocalDateTime;

/**
 * Sacred User DTO
 */
public class UserDto {
    
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    
    // Sacred Consciousness Properties
    private String consciousnessLevel;
    private String personalVision;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastActiveAt;
    
    // Constructors
    public UserDto() {}
    
    public UserDto(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public String getConsciousnessLevel() { return consciousnessLevel; }
    public void setConsciousnessLevel(String consciousnessLevel) { this.consciousnessLevel = consciousnessLevel; }
    
    public String getPersonalVision() { return personalVision; }
    public void setPersonalVision(String personalVision) { this.personalVision = personalVision; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getLastActiveAt() { return lastActiveAt; }
    public void setLastActiveAt(LocalDateTime lastActiveAt) { this.lastActiveAt = lastActiveAt; }
}
