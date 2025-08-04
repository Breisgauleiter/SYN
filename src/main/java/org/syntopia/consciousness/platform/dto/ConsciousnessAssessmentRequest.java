package org.syntopia.consciousness.platform.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Sacred Consciousness Assessment Request DTO
 */
public class ConsciousnessAssessmentRequest {
    
    @NotNull
    private String primeGiftsData; // Simplified for MVP
    
    // Constructors
    public ConsciousnessAssessmentRequest() {}
    
    public ConsciousnessAssessmentRequest(String primeGiftsData) {
        this.primeGiftsData = primeGiftsData;
    }
    
    // Getters and setters
    public String getPrimeGiftsData() {
        return primeGiftsData;
    }
    
    public void setPrimeGiftsData(String primeGiftsData) {
        this.primeGiftsData = primeGiftsData;
    }
}
