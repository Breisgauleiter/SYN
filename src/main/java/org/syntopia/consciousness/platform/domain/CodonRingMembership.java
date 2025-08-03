package org.syntopia.consciousness.platform.domain;

/**
 * Codon Ring Membership for the 22 Sacred Rings of Community
 * 
 * Based on Richard Rudd's Gene Keys system, the 22 Codon Rings
 * represent archetypal community groups that provide natural
 * affinity and collaboration patterns.
 */
public enum CodonRingMembership {
    
    // Ring of Fire (Rings 1-11) - Activation and Initiation
    RING_OF_LIGHT(1, "Ring of Light", "Illumination and Inspiration", "Bringing light to darkness"),
    RING_OF_LIFE_DEATH(2, "Ring of Life & Death", "Transformation Mastery", "Cycles of renewal and rebirth"),
    RING_OF_HUMANITY(3, "Ring of Humanity", "Human Connection", "Building bridges between people"),
    RING_OF_ILLUMINATION(4, "Ring of Illumination", "Wisdom Sharing", "Enlightening others through knowledge"),
    RING_OF_MATTER(5, "Ring of Matter", "Material Mastery", "Conscious relationship with physical world"),
    RING_OF_GAIA(6, "Ring of Gaia", "Planetary Consciousness", "Healing and serving Mother Earth"),
    RING_OF_SEEKING(7, "Ring of Seeking", "Quest for Truth", "Eternal seekers and questioners"),
    RING_OF_WATER(8, "Ring of Water", "Emotional Flow", "Masters of emotional intelligence"),
    RING_OF_FIRE(9, "Ring of Fire", "Passion and Energy", "Transformational fire and inspiration"),
    RING_OF_TRIALS(10, "Ring of Trials", "Testing and Growth", "Learning through challenges"),
    RING_OF_ARTISTRY(11, "Ring of Artistry", "Creative Expression", "Artists and creative innovators"),
    
    // Ring of Water (Rings 12-22) - Integration and Mastery
    RING_OF_ENDEAVOUR(12, "Ring of Endeavour", "Persistent Action", "Dedicated workers and builders"),
    RING_OF_PERSONAL_ALCHEMY(13, "Ring of Personal Alchemy", "Self Transformation", "Inner work and development"),
    RING_OF_PROSPERITY(14, "Ring of Prosperity", "Abundance Consciousness", "Creating and sharing wealth"),
    RING_OF_THE_WHIRLWIND(15, "Ring of the Whirlwind", "Chaos to Order", "Mastering turbulent energies"),
    RING_OF_DESTINY(16, "Ring of Destiny", "Life Purpose", "Fulfilling soul mission"),
    RING_OF_SECRETS(17, "Ring of Secrets", "Hidden Wisdom", "Keepers of sacred knowledge"),
    RING_OF_HEALING(18, "Ring of Healing", "Restoration", "Healers and medicine people"),
    RING_OF_NO_RETURN(19, "Ring of No Return", "Ultimate Commitment", "Point of no return choices"),
    RING_OF_DIVINE_WILL(20, "Ring of Divine Will", "Sacred Purpose", "Alignment with cosmic will"),
    RING_OF_HUMAN_BONDAGE(21, "Ring of Human Bondage", "Freedom Through Service", "Liberation through love"),
    RING_OF_SYNTHESIS(22, "Ring of Synthesis", "Unity Consciousness", "Integration of all opposites");
    
    private final int ringNumber;
    private final String ringName;
    private final String theme;
    private final String description;
    
    CodonRingMembership(int ringNumber, String ringName, String theme, String description) {
        this.ringNumber = ringNumber;
        this.ringName = ringName;
        this.theme = theme;
        this.description = description;
    }
    
    // Gene Keys to Codon Ring Mapping
    public static CodonRingMembership getByGeneKey(int geneKeyNumber) {
        // Simplified mapping - in practice this would use the full I'Ching codon calculations
        int ringIndex = ((geneKeyNumber - 1) % 22);
        return values()[ringIndex];
    }
    
    public static CodonRingMembership getByRingNumber(int ringNumber) {
        for (CodonRingMembership ring : values()) {
            if (ring.ringNumber == ringNumber) {
                return ring;
            }
        }
        throw new IllegalArgumentException("Invalid ring number: " + ringNumber);
    }
    
    // Community Formation Methods
    public boolean isCompatibleWith(CodonRingMembership other) {
        // Rings have natural affinities and tensions
        if (this == other) return true; // Same ring = perfect compatibility
        
        // Fire rings (1-11) work well with Water rings (12-22)
        boolean thisIsFire = this.ringNumber <= 11;
        boolean otherIsFire = other.ringNumber <= 11;
        
        if (thisIsFire != otherIsFire) return true; // Fire + Water = good balance
        
        // Adjacent rings often complement each other
        int distance = Math.abs(this.ringNumber - other.ringNumber);
        return distance == 1 || distance == 11; // Adjacent or opposite
    }
    
    public CollaborationStyle getCollaborationStyle() {
        if (ringNumber <= 11) {
            return CollaborationStyle.INITIATING; // Fire rings initiate
        } else {
            return CollaborationStyle.INTEGRATING; // Water rings integrate
        }
    }
    
    public enum CollaborationStyle {
        INITIATING("Initiating", "Starts projects and sparks innovation"),
        INTEGRATING("Integrating", "Builds consensus and creates harmony");
        
        private final String name;
        private final String description;
        
        CollaborationStyle(String name, String description) {
            this.name = name;
            this.description = description;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
    }
    
    // Sacred Geometry Integration
    public String getSacredColor() {
        // Color coding for sacred geometry visualization
        if (ringNumber <= 11) {
            // Fire rings: Red to Orange spectrum
            float hue = (ringNumber - 1) * 20f; // 0-200 degrees
            return String.format("hsl(%.0f, 80%%, 60%%)", hue);
        } else {
            // Water rings: Blue to Purple spectrum  
            float hue = 200f + ((ringNumber - 12) * 15f); // 200-365 degrees
            return String.format("hsl(%.0f, 70%%, 55%%)", hue);
        }
    }
    
    public double getSacredAngle() {
        // Position on 22-pointed star
        return (ringNumber - 1) * (360.0 / 22.0);
    }
    
    // Getters
    public int getRingNumber() { return ringNumber; }
    public String getRingName() { return ringName; }
    public String getTheme() { return theme; }
    public String getDescription() { return description; }
    
    public String getFullName() {
        return ringNumber + ". " + ringName;
    }
    
    @Override
    public String toString() {
        return getFullName() + " - " + theme;
    }
}
