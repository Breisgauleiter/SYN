package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Consciousness State Tracking for Sacred Technology Platform
 * 
 * Represents a user's current consciousness state based on their
 * Gene Keys activation sequence and SCL progression.
 */
@Embeddable
public class ConsciousnessState {
    
    @NotNull
    @Column(name = "current_shadow_frequency")
    private Integer currentShadowFrequency;
    
    @NotNull
    @Column(name = "current_gift_frequency") 
    private Integer currentGiftFrequency;
    
    @Column(name = "current_siddhi_frequency")
    private Integer currentSiddhiFrequency;
    
    @NotNull
    @Column(name = "primary_activated_gene_key")
    private Integer primaryActivatedGeneKey;
    
    @Column(name = "consciousness_coherence_percentage")
    private Double consciousnessCoherence;
    
    // Constructors
    public ConsciousnessState() {}
    
    public ConsciousnessState(Integer primaryActivatedGeneKey, Integer shadowFreq, Integer giftFreq) {
        this.primaryActivatedGeneKey = primaryActivatedGeneKey;
        this.currentShadowFrequency = shadowFreq;
        this.currentGiftFrequency = giftFreq;
        this.consciousnessCoherence = 0.0;
    }
    
    // Sacred Methods
    public boolean isInShadowFrequency() {
        return currentShadowFrequency > currentGiftFrequency;
    }
    
    public boolean isInGiftFrequency() {
        return currentGiftFrequency > currentShadowFrequency && 
               (currentSiddhiFrequency == null || currentGiftFrequency > currentSiddhiFrequency);
    }
    
    public boolean isInSiddhiFrequency() {
        return currentSiddhiFrequency != null && 
               currentSiddhiFrequency > currentGiftFrequency && 
               currentSiddhiFrequency > currentShadowFrequency;
    }
    
    // Getters and Setters
    public Integer getCurrentShadowFrequency() { return currentShadowFrequency; }
    public void setCurrentShadowFrequency(Integer currentShadowFrequency) { 
        this.currentShadowFrequency = currentShadowFrequency; 
    }
    
    public Integer getCurrentGiftFrequency() { return currentGiftFrequency; }
    public void setCurrentGiftFrequency(Integer currentGiftFrequency) { 
        this.currentGiftFrequency = currentGiftFrequency; 
    }
    
    public Integer getCurrentSiddhiFrequency() { return currentSiddhiFrequency; }
    public void setCurrentSiddhiFrequency(Integer currentSiddhiFrequency) { 
        this.currentSiddhiFrequency = currentSiddhiFrequency; 
    }
    
    public Integer getPrimaryActivatedGeneKey() { return primaryActivatedGeneKey; }
    public void setPrimaryActivatedGeneKey(Integer primaryActivatedGeneKey) { 
        this.primaryActivatedGeneKey = primaryActivatedGeneKey; 
    }
    
    public Double getConsciousnessCoherence() { return consciousnessCoherence; }
    public void setConsciousnessCoherence(Double consciousnessCoherence) { 
        this.consciousnessCoherence = consciousnessCoherence; 
    }
}
