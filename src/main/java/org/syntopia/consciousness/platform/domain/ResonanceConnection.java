package org.syntopia.consciousness.platform.domain;

/**
 * Sacred consciousness connection for service layer processing
 * Simplified view of consciousness resonance for calculations
 */
public class ResonanceConnection {
    private String fromUserId;
    private String toUserId;
    private Double resonanceStrength;
    private Double geneKeysCompatibility;
    private Double consciousnessLevelSynergy;
    
    // Constructors
    public ResonanceConnection() {}
    
    public ResonanceConnection(String fromUserId, String toUserId, Double resonanceStrength) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.resonanceStrength = resonanceStrength;
    }
    
    // Getters and setters
    public String getFromUserId() {
        return fromUserId;
    }
    
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }
    
    public String getToUserId() {
        return toUserId;
    }
    
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
    
    public Double getResonanceStrength() {
        return resonanceStrength;
    }
    
    public void setResonanceStrength(Double resonanceStrength) {
        this.resonanceStrength = resonanceStrength;
    }
    
    public Double getGeneKeysCompatibility() {
        return geneKeysCompatibility;
    }
    
    public void setGeneKeysCompatibility(Double geneKeysCompatibility) {
        this.geneKeysCompatibility = geneKeysCompatibility;
    }
    
    public Double getConsciousnessLevelSynergy() {
        return consciousnessLevelSynergy;
    }
    
    public void setConsciousnessLevelSynergy(Double consciousnessLevelSynergy) {
        this.consciousnessLevelSynergy = consciousnessLevelSynergy;
    }
    
    // Sacred consciousness utility methods
    public boolean hasStrongResonance() {
        return resonanceStrength != null && resonanceStrength > 0.7;
    }
    
    public boolean isGoldenRatioResonance() {
        return resonanceStrength != null && Math.abs(resonanceStrength - 1.618) < 0.1;
    }
}
