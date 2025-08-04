package org.syntopia.consciousness.platform.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Sacred Registration Request DTO
 */
public class RegisterRequestDto {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    private String firstName;
    private String lastName;
    
    private boolean acceptTerms = false;
    private boolean acceptPrivacyPolicy = false;
    
    // Sacred Consciousness Properties
    private String initialConsciousnessLevel = "AWAKENING";
    private List<String> sacredIntentions;
    private String personalVision;
    
    // Constructors
    public RegisterRequestDto() {}
    
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public boolean isAcceptTerms() { return acceptTerms; }
    public void setAcceptTerms(boolean acceptTerms) { this.acceptTerms = acceptTerms; }
    
    public boolean isAcceptPrivacyPolicy() { return acceptPrivacyPolicy; }
    public void setAcceptPrivacyPolicy(boolean acceptPrivacyPolicy) { this.acceptPrivacyPolicy = acceptPrivacyPolicy; }
    
    public String getInitialConsciousnessLevel() { return initialConsciousnessLevel; }
    public void setInitialConsciousnessLevel(String initialConsciousnessLevel) { this.initialConsciousnessLevel = initialConsciousnessLevel; }
    
    public List<String> getSacredIntentions() { return sacredIntentions; }
    public void setSacredIntentions(List<String> sacredIntentions) { this.sacredIntentions = sacredIntentions; }
    
    public String getPersonalVision() { return personalVision; }
    public void setPersonalVision(String personalVision) { this.personalVision = personalVision; }
}
