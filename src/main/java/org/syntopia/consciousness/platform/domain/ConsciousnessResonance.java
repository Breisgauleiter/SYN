package org.syntopia.consciousness.platform.domain;

import java.time.LocalDateTime;

/**
 * Sacred consciousness resonance connection between two users in the SYNtopia network
 * Implements TAO Association pattern for ArangoDB edges
 */
public class ConsciousnessResonance {
    private String id;
    private String from;
    private String to;
    private String type = "consciousness_resonance";
    private Double strength;
    private ResonanceFactors resonanceFactors;
    private Boolean isActive;
    private LocalDateTime lastCalculated;
    private LocalDateTime createdAt;
    
    // Constructors
    public ConsciousnessResonance() {}
    
    public ConsciousnessResonance(String from, String to, Double strength) {
        this.from = from;
        this.to = to;
        this.strength = strength;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.lastCalculated = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Double getStrength() {
        return strength;
    }
    
    public void setStrength(Double strength) {
        this.strength = strength;
    }
    
    public ResonanceFactors getResonanceFactors() {
        return resonanceFactors;
    }
    
    public void setResonanceFactors(ResonanceFactors resonanceFactors) {
        this.resonanceFactors = resonanceFactors;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getLastCalculated() {
        return lastCalculated;
    }
    
    public void setLastCalculated(LocalDateTime lastCalculated) {
        this.lastCalculated = lastCalculated;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Convenience methods for sacred consciousness calculations
    public Double getResonanceStrength() {
        return strength;
    }
    
    public boolean isActiveResonance() {
        return isActive != null && isActive;
    }
}
