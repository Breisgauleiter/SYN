package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Sacred consciousness resonance connection between two users in the SYNtopia network
 * Implements TAO Association pattern for ArangoDB edges
 */
@Entity
@Table(name = "consciousness_resonance")
public class ConsciousnessResonance {
    @Id
    private String id;
    
    @Column(name = "source_user_id")
    private String sourceUserId; // Renamed from 'from' for JPA compatibility
    
    @Column(name = "target_user_id") 
    private String targetUserId; // Renamed from 'to' for JPA compatibility
    
    @Column(name = "resonance_type")
    private String type = "consciousness_resonance";
    
    private Double strength;
    
    @Embedded
    private ResonanceFactors resonanceFactors;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "last_calculated")
    private LocalDateTime lastCalculated;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public ConsciousnessResonance() {}
    
    public ConsciousnessResonance(String from, String to, Double strength) {
        this.sourceUserId = from;
        this.targetUserId = to;
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
    
    public String getSourceUserId() {
        return sourceUserId;
    }
    
    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
    
    public String getTargetUserId() {
        return targetUserId;
    }
    
    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }
    
    // Legacy compatibility methods for ArangoDB
    public String getFrom() {
        return sourceUserId;
    }
    
    public void setFrom(String from) {
        this.sourceUserId = from;
    }
    
    public String getTo() {
        return targetUserId;
    }
    
    public void setTo(String to) {
        this.targetUserId = to;
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
