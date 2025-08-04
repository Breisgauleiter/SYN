package org.syntopia.consciousness.platform.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Sacred Codon Ring Request DTO
 */
public class CodonRingRequest {
    
    @NotNull
    private String userId;
    
    // For MVP - simplified without PrimeGifts
    private String primeGiftsData;
    
    // Constructors
    public CodonRingRequest() {}
    
    public CodonRingRequest(String userId, String primeGiftsData) {
        this.userId = userId;
        this.primeGiftsData = primeGiftsData;
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPrimeGiftsData() {
        return primeGiftsData;
    }
    
    public void setPrimeGiftsData(String primeGiftsData) {
        this.primeGiftsData = primeGiftsData;
    }
}
