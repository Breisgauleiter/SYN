package org.syntopia.consciousness.platform.dto.auth;

import java.util.List;

/**
 * Sacred Profile Update DTO
 */
public class ProfileUpdateDto {
    
    private String firstName;
    private String lastName;
    private String avatar;
    private String personalVision;
    private List<String> sacredIntentions;
    
    // Constructors
    public ProfileUpdateDto() {}
    
    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public String getPersonalVision() { return personalVision; }
    public void setPersonalVision(String personalVision) { this.personalVision = personalVision; }
    
    public List<String> getSacredIntentions() { return sacredIntentions; }
    public void setSacredIntentions(List<String> sacredIntentions) { this.sacredIntentions = sacredIntentions; }
}
