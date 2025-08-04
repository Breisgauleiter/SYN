package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Hologenetic Profile - Complete Gene Keys System Integration
 * 
 * Implements Richard Rudd's Gene Keys system with all 4 Sequences:
 * - Activation Sequence: Life's Work, Evolution, Radiance, Purpose
 * - Venus Sequence: Attraction, IQ, EQ, SQ (Spiritual Intelligence)  
 * - Pearl Sequence: Prosperity through service and gift-giving
 * - Star Pearl Sequence: Leadership through consciousness evolution
 * 
 * Provides the foundation for conscious team formation, synchronicity
 * detection, and authentic business collaboration.
 */
@Embeddable
public class HologeneticProfile {
    
    // Personal Information for Calculation
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(name = "birth_time")
    private String birthTime; // Format: "HH:MM"
    
    @Column(name = "birth_location")
    private String birthLocation;
    
    @Column(name = "birth_timezone")
    private String birthTimezone;
    
    // Activation Sequence - Core Life Path
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "lifework_key")),
        @AttributeOverride(name = "line", column = @Column(name = "lifework_line")),
        @AttributeOverride(name = "shadowAspect", column = @Column(name = "lifework_shadow")),
        @AttributeOverride(name = "giftAspect", column = @Column(name = "lifework_gift")),
        @AttributeOverride(name = "siddhiAspect", column = @Column(name = "lifework_siddhi"))
    })
    private GeneKey lifeworkGeneKey;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "evolution_key")),
        @AttributeOverride(name = "line", column = @Column(name = "evolution_line")),
        @AttributeOverride(name = "shadowAspect", column = @Column(name = "evolution_shadow")),
        @AttributeOverride(name = "giftAspect", column = @Column(name = "evolution_gift")),
        @AttributeOverride(name = "siddhiAspect", column = @Column(name = "evolution_siddhi"))
    })
    private GeneKey evolutionGeneKey;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "radiance_key")),
        @AttributeOverride(name = "line", column = @Column(name = "radiance_line")),
        @AttributeOverride(name = "shadowAspect", column = @Column(name = "radiance_shadow")),
        @AttributeOverride(name = "giftAspect", column = @Column(name = "radiance_gift")),
        @AttributeOverride(name = "siddhiAspect", column = @Column(name = "radiance_siddhi"))
    })
    private GeneKey radianceGeneKey;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "purpose_key")),
        @AttributeOverride(name = "line", column = @Column(name = "purpose_line")),
        @AttributeOverride(name = "shadowAspect", column = @Column(name = "purpose_shadow")),
        @AttributeOverride(name = "giftAspect", column = @Column(name = "purpose_gift")),
        @AttributeOverride(name = "siddhiAspect", column = @Column(name = "purpose_siddhi"))
    })
    private GeneKey purposeGeneKey;
    
    // Venus Sequence - Relationships and Intelligence  
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "attraction_key")),
        @AttributeOverride(name = "line", column = @Column(name = "attraction_line"))
    })
    private GeneKey attractionGeneKey;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "iq_key")),
        @AttributeOverride(name = "line", column = @Column(name = "iq_line"))
    })
    private GeneKey intellectualGeneKey; // IQ
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "eq_key")),
        @AttributeOverride(name = "line", column = @Column(name = "eq_line"))
    })
    private GeneKey emotionalGeneKey; // EQ
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "keyNumber", column = @Column(name = "sq_key")),
        @AttributeOverride(name = "line", column = @Column(name = "sq_line"))
    })
    private GeneKey spiritualGeneKey; // SQ
    
    // Pearl Sequence - Prosperity and Service
    @ElementCollection
    @CollectionTable(name = "pearl_sequence_keys")
    private List<GeneKey> pearlSequence = new ArrayList<>();
    
    // Star Pearl Sequence - Leadership and Higher Service
    @ElementCollection  
    @CollectionTable(name = "star_pearl_sequence_keys")
    private List<GeneKey> starPearlSequence = new ArrayList<>();
    
    // Codon Ring Associations (22 Rings)
    @ElementCollection
    @Column(name = "codon_ring_numbers")
    private Set<Integer> associatedCodonRings = new HashSet<>();
    
    // Profile Completion and Validation
    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;
    
    @Column(name = "is_verified")
    private boolean isVerified = false;
    
    @Column(name = "verification_notes")
    private String verificationNotes;
    
    // Constructors
    public HologeneticProfile() {}
    
    public HologeneticProfile(LocalDate birthDate, String birthTime, String birthLocation, String birthTimezone) {
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.birthLocation = birthLocation;
        this.birthTimezone = birthTimezone;
        this.calculatedAt = LocalDateTime.now();
    }
    
    // Core Gene Keys Calculation Methods
    public void calculateActivationSequence() {
        // TODO: Implement I'Ching / Genetic Code calculation based on birth data
        // This will calculate the 4 primary Gene Keys from birth chart
        
        // Placeholder implementation - replace with actual calculation
        this.lifeworkGeneKey = new GeneKey(1, 1); // Example: Gene Key 1, Line 1
        this.evolutionGeneKey = new GeneKey(2, 2);
        this.radianceGeneKey = new GeneKey(3, 3);
        this.purposeGeneKey = new GeneKey(4, 4);
        
        this.calculatedAt = LocalDateTime.now();
    }
    
    public void calculateVenusSequence() {
        // TODO: Implement Venus Sequence calculation
        // Based on 88-day Venus cycle and birth data
        
        this.attractionGeneKey = new GeneKey(5, 1);
        this.intellectualGeneKey = new GeneKey(6, 2);
        this.emotionalGeneKey = new GeneKey(7, 3);
        this.spiritualGeneKey = new GeneKey(8, 4);
    }
    
    public void calculatePearlSequence() {
        // TODO: Implement Pearl Sequence calculation
        // Based on prosperity and service patterns
        
        pearlSequence.clear();
        // Add calculated Pearl sequence Gene Keys
        for (int i = 9; i <= 12; i++) {
            pearlSequence.add(new GeneKey(i, (i % 6) + 1));
        }
    }
    
    public void calculateStarPearlSequence() {
        // TODO: Implement Star Pearl (Leadership) Sequence calculation
        
        starPearlSequence.clear();
        // Add calculated Star Pearl sequence Gene Keys
        for (int i = 13; i <= 16; i++) {
            starPearlSequence.add(new GeneKey(i, (i % 6) + 1));
        }
    }
    
    public void calculateCodonRingAssociations() {
        // TODO: Calculate which of the 22 Codon Rings this profile belongs to
        // Based on Gene Key combinations and consciousness resonance
        
        associatedCodonRings.clear();
        // Example associations - replace with actual calculation
        if (lifeworkGeneKey != null) {
            int ringNumber = (lifeworkGeneKey.getKeyNumber() % 22) + 1;
            associatedCodonRings.add(ringNumber);
        }
    }
    
    // Compatibility and Matching Methods
    public boolean isCompatibleWithCodonRing(int ringNumber) {
        return associatedCodonRings.contains(ringNumber);
    }
    
    public Set<Integer> getCompatibleCodonRings() {
        return new HashSet<>(associatedCodonRings);
    }
    
    public double calculateResonanceWith(HologeneticProfile other) {
        if (other == null || !this.isComplete() || !other.isComplete()) {
            return 0.0;
        }
        
        double resonance = 0.0;
        int comparisonCount = 0;
        
        // Compare Activation Sequence resonance
        if (this.lifeworkGeneKey != null && other.lifeworkGeneKey != null) {
            resonance += calculateGeneKeyResonance(this.lifeworkGeneKey, other.lifeworkGeneKey);
            comparisonCount++;
        }
        
        if (this.evolutionGeneKey != null && other.evolutionGeneKey != null) {
            resonance += calculateGeneKeyResonance(this.evolutionGeneKey, other.evolutionGeneKey);
            comparisonCount++;
        }
        
        // Compare Venus Sequence resonance
        if (this.attractionGeneKey != null && other.attractionGeneKey != null) {
            resonance += calculateGeneKeyResonance(this.attractionGeneKey, other.attractionGeneKey);
            comparisonCount++;
        }
        
        // Compare Codon Ring overlap
        Set<Integer> commonRings = new HashSet<>(this.associatedCodonRings);
        commonRings.retainAll(other.associatedCodonRings);
        double ringResonance = (double) commonRings.size() / Math.max(this.associatedCodonRings.size(), other.associatedCodonRings.size());
        resonance += ringResonance;
        comparisonCount++;
        
        return comparisonCount > 0 ? resonance / comparisonCount : 0.0;
    }
    
    private double calculateGeneKeyResonance(GeneKey key1, GeneKey key2) {
        if (key1.equals(key2)) return 1.0; // Perfect match
        
        // Calculate resonance based on:
        // 1. Complementary Gene Keys (e.g., 1-2, 3-50, etc.)
        // 2. Same line numbers
        // 3. Same codon ring associations
        
        double resonance = 0.0;
        
        // Same line = some resonance
        if (key1.getLine() == key2.getLine()) {
            resonance += 0.3;
        }
        
        // Complementary keys = high resonance  
        if (areComplementaryKeys(key1.getKeyNumber(), key2.getKeyNumber())) {
            resonance += 0.7;
        }
        
        return Math.min(resonance, 1.0);
    }
    
    private boolean areComplementaryKeys(int key1, int key2) {
        // TODO: Implement I'Ching complementary pairs
        // For now, simple modulo check
        return (key1 + key2) % 64 == 0;
    }
    
    // Validation Methods
    public boolean isComplete() {
        return lifeworkGeneKey != null &&
               evolutionGeneKey != null &&
               radianceGeneKey != null &&
               purposeGeneKey != null &&
               attractionGeneKey != null &&
               calculatedAt != null;
    }
    
    public boolean hasVenusSequence() {
        return attractionGeneKey != null &&
               intellectualGeneKey != null &&
               emotionalGeneKey != null &&
               spiritualGeneKey != null;
    }
    
    public boolean hasPearlSequence() {
        return !pearlSequence.isEmpty();
    }
    
    public boolean hasStarPearlSequence() {
        return !starPearlSequence.isEmpty();
    }
    
    // Getters and Setters
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    
    public String getBirthTime() { return birthTime; }
    public void setBirthTime(String birthTime) { this.birthTime = birthTime; }
    
    public String getBirthLocation() { return birthLocation; }
    public void setBirthLocation(String birthLocation) { this.birthLocation = birthLocation; }
    
    public String getBirthTimezone() { return birthTimezone; }
    public void setBirthTimezone(String birthTimezone) { this.birthTimezone = birthTimezone; }
    
    public GeneKey getLifeworkGeneKey() { return lifeworkGeneKey; }
    public void setLifeworkGeneKey(GeneKey lifeworkGeneKey) { this.lifeworkGeneKey = lifeworkGeneKey; }
    
    public GeneKey getEvolutionGeneKey() { return evolutionGeneKey; }
    public void setEvolutionGeneKey(GeneKey evolutionGeneKey) { this.evolutionGeneKey = evolutionGeneKey; }
    
    public GeneKey getRadianceGeneKey() { return radianceGeneKey; }
    public void setRadianceGeneKey(GeneKey radianceGeneKey) { this.radianceGeneKey = radianceGeneKey; }
    
    public GeneKey getPurposeGeneKey() { return purposeGeneKey; }
    public void setPurposeGeneKey(GeneKey purposeGeneKey) { this.purposeGeneKey = purposeGeneKey; }
    
    public GeneKey getAttractionGeneKey() { return attractionGeneKey; }
    public void setAttractionGeneKey(GeneKey attractionGeneKey) { this.attractionGeneKey = attractionGeneKey; }
    
    public GeneKey getIntellectualGeneKey() { return intellectualGeneKey; }
    public void setIntellectualGeneKey(GeneKey intellectualGeneKey) { this.intellectualGeneKey = intellectualGeneKey; }
    
    public GeneKey getEmotionalGeneKey() { return emotionalGeneKey; }
    public void setEmotionalGeneKey(GeneKey emotionalGeneKey) { this.emotionalGeneKey = emotionalGeneKey; }
    
    public GeneKey getSpiritualGeneKey() { return spiritualGeneKey; }
    public void setSpiritualGeneKey(GeneKey spiritualGeneKey) { this.spiritualGeneKey = spiritualGeneKey; }
    
    public List<GeneKey> getPearlSequence() { return pearlSequence; }
    public void setPearlSequence(List<GeneKey> pearlSequence) { this.pearlSequence = pearlSequence; }
    
    public List<GeneKey> getStarPearlSequence() { return starPearlSequence; }
    public void setStarPearlSequence(List<GeneKey> starPearlSequence) { this.starPearlSequence = starPearlSequence; }
    
    public Set<Integer> getAssociatedCodonRings() { return associatedCodonRings; }
    public void setAssociatedCodonRings(Set<Integer> associatedCodonRings) { 
        this.associatedCodonRings = associatedCodonRings; 
    }
    
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; }
    
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
    
    public String getVerificationNotes() { return verificationNotes; }
    public void setVerificationNotes(String verificationNotes) { this.verificationNotes = verificationNotes; }
    
    // Simple Builder Pattern for Service Layer
    public static HologeneticProfileBuilder builder() {
        return new HologeneticProfileBuilder();
    }
    
    public static class HologeneticProfileBuilder {
        private HologeneticProfile profile = new HologeneticProfile();
        
        public HologeneticProfileBuilder birthDate(LocalDate birthDate) {
            profile.setBirthDate(birthDate);
            return this;
        }
        
        public HologeneticProfileBuilder birthTime(String birthTime) {
            profile.setBirthTime(birthTime);
            return this;
        }
        
        public HologeneticProfileBuilder birthLocation(String birthLocation) {
            profile.setBirthLocation(birthLocation);
            return this;
        }
        
        public HologeneticProfileBuilder lifeworkGeneKey(GeneKey lifeworkGeneKey) {
            profile.setLifeworkGeneKey(lifeworkGeneKey);
            return this;
        }
        
        public HologeneticProfileBuilder evolutionGeneKey(GeneKey evolutionGeneKey) {
            profile.setEvolutionGeneKey(evolutionGeneKey);
            return this;
        }
        
        public HologeneticProfileBuilder radianceGeneKey(GeneKey radianceGeneKey) {
            profile.setRadianceGeneKey(radianceGeneKey);
            return this;
        }
        
        public HologeneticProfileBuilder purposeGeneKey(GeneKey purposeGeneKey) {
            profile.setPurposeGeneKey(purposeGeneKey);
            return this;
        }
        
        public HologeneticProfile build() {
            profile.setCalculatedAt(LocalDateTime.now());
            return profile;
        }
    }
    
    @Override
    public String toString() {
        return "HologeneticProfile{" +
                "birthDate=" + birthDate +
                ", lifeworkGeneKey=" + lifeworkGeneKey +
                ", evolutionGeneKey=" + evolutionGeneKey +
                ", isComplete=" + isComplete() +
                ", calculatedAt=" + calculatedAt +
                '}';
    }
}
