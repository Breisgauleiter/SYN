package org.syntopia.consciousness.platform.domain;

/**
 * Sacred resonance calculation factors for consciousness connections
 * Used in TAO Association pattern for detailed edge properties
 */
public class ResonanceFactors {
    private Double geneKeysCompatibility;
    private Double consciousnessLevelSynergy;
    private Double businessTrackAlignment;
    private Double synchronicityFactor;
    
    // Constructors
    public ResonanceFactors() {}
    
    public ResonanceFactors(Double geneKeysCompatibility, Double consciousnessLevelSynergy, 
                          Double businessTrackAlignment, Double synchronicityFactor) {
        this.geneKeysCompatibility = geneKeysCompatibility;
        this.consciousnessLevelSynergy = consciousnessLevelSynergy;
        this.businessTrackAlignment = businessTrackAlignment;
        this.synchronicityFactor = synchronicityFactor;
    }
    
    // Getters and setters
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
    
    public Double getBusinessTrackAlignment() {
        return businessTrackAlignment;
    }
    
    public void setBusinessTrackAlignment(Double businessTrackAlignment) {
        this.businessTrackAlignment = businessTrackAlignment;
    }
    
    public Double getSynchronicityFactor() {
        return synchronicityFactor;
    }
    
    public void setSynchronicityFactor(Double synchronicityFactor) {
        this.synchronicityFactor = synchronicityFactor;
    }
    
    // Sacred consciousness calculation method
    public Double calculateOverallResonance() {
        if (geneKeysCompatibility == null || consciousnessLevelSynergy == null || 
            businessTrackAlignment == null || synchronicityFactor == null) {
            return 0.0;
        }
        
        // Golden ratio weighted sacred consciousness formula
        Double goldenRatio = 1.618;
        return (geneKeysCompatibility * goldenRatio + 
                consciousnessLevelSynergy + 
                businessTrackAlignment + 
                synchronicityFactor) / (goldenRatio + 3);
    }
}
