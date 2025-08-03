package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

/**
 * Consciousness Level representation with Fibonacci-based progression
 * 
 * SCL (SYN Contribution Level) System:
 * - Levels 1-25 for complete consciousness evolution
 * - Fibonacci progression for natural growth patterns
 * - Sacred geometry integration for UI visualization
 * - Business track advancement requirements
 */
@Embeddable
public class ConsciousnessLevel {
    
    @Column(name = "scl_level")
    private int level;
    
    @Column(name = "level_name")
    private String levelName;
    
    @Column(name = "level_description")
    private String description;
    
    @Column(name = "required_contributions")
    private int requiredContributions;
    
    @Column(name = "fibonacci_index")
    private int fibonacciIndex;
    
    // SCL Level Definitions with Fibonacci Progression
    private static final SCLDefinition[] SCL_DEFINITIONS = {
        new SCLDefinition(1, "Erwachen", "Profil einrichten und erste Schritte", 0, 0),
        new SCLDefinition(2, "Entdecken", "Platform erkunden und Gene Keys berechnen", 1, 1),
        new SCLDefinition(3, "Verbinden", "Erste Community-Connections aufbauen", 1, 2),
        new SCLDefinition(4, "Lernen", "Business Track wählen und Skills entwickeln", 2, 3),
        new SCLDefinition(5, "Beitragen", "Erste echte Platform-Contributions", 3, 4),
        new SCLDefinition(6, "Wachsen", "Regelmäßige Aktivität und Engagement", 5, 5),
        new SCLDefinition(7, "Vernetzen", "Codon Ring Communities beitreten", 8, 6),
        new SCLDefinition(8, "Entwickeln", "Advanced Skills und Spezialisierung", 13, 7),
        new SCLDefinition(9, "Innovieren", "Eigene Ideen und Projekte einbringen", 21, 8),
        new SCLDefinition(10, "Kollaborieren", "Cross-Track Team-Projekte leiten", 34, 9),
        new SCLDefinition(11, "Optimieren", "Platform-Verbesserungen vorschlagen", 55, 10),
        new SCLDefinition(12, "Mentorieren", "Neue User bei Onboarding unterstützen", 89, 11),
        new SCLDefinition(13, "Koordinieren", "Team-Coordination und Synergien", 144, 12),
        new SCLDefinition(14, "Strategieren", "Business-Strategie mitentwickeln", 233, 13),
        new SCLDefinition(15, "Führen", "Leadership in Business Track", 377, 14),
        new SCLDefinition(16, "Visionieren", "Platform-Vision mitgestalten", 610, 15),
        new SCLDefinition(17, "Skalieren", "Skalierungs-Strategien entwickeln", 987, 16),
        new SCLDefinition(18, "Globalisieren", "Internationale Expansion", 1597, 17),
        new SCLDefinition(19, "Systematisieren", "Systemische Platform-Architektur", 2584, 18),
        new SCLDefinition(20, "Revolutionieren", "Industrie-disruptive Innovation", 4181, 19),
        new SCLDefinition(21, "Harmonisieren", "Planetare Bewusstseins-Integration", 6765, 20),
        new SCLDefinition(22, "Transzendieren", "Bewusstseins-Technologie Pionier", 10946, 21),
        new SCLDefinition(23, "Manifestieren", "Neue Realität co-kreieren", 17711, 22),
        new SCLDefinition(24, "Einwirken", "Planetare Transformation", 28657, 23),
        new SCLDefinition(25, "Vollenden", "Unity Consciousness Integration", 46368, 24)
    };
    
    // Constructors
    public ConsciousnessLevel() {
        this(1); // Default to level 1
    }
    
    public ConsciousnessLevel(int level) {
        setLevel(level);
    }
    
    // Level Management
    public void setLevel(int level) {
        if (level < 1 || level > 25) {
            throw new IllegalArgumentException("SCL Level must be between 1 and 25");
        }
        
        SCLDefinition definition = SCL_DEFINITIONS[level - 1];
        this.level = level;
        this.levelName = definition.name;
        this.description = definition.description;
        this.requiredContributions = definition.requiredContributions;
        this.fibonacciIndex = definition.fibonacciIndex;
    }
    
    // Progression Methods
    public boolean canProgressTo(int targetLevel) {
        return targetLevel == this.level + 1 && targetLevel <= 25;
    }
    
    public ConsciousnessLevel getNextLevel() {
        if (level < 25) {
            return new ConsciousnessLevel(level + 1);
        }
        return this; // Already at max level
    }
    
    public ConsciousnessLevel getPreviousLevel() {
        if (level > 1) {
            return new ConsciousnessLevel(level - 1);
        }
        return this; // Already at min level
    }
    
    // Sacred Geometry Integration
    public double getGoldenRatioPosition() {
        // Position on golden ratio spiral based on level
        return level * 1.618033988749895;
    }
    
    public int getFibonacciValue() {
        int[] fibonacci = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368};
        return fibonacci[Math.min(fibonacciIndex, fibonacci.length - 1)];
    }
    
    public String getSacredGeometryColor() {
        // Color mapping for sacred geometry visualization
        if (level <= 5) return "#1a1a2e"; // Deep space blue
        if (level <= 10) return "#16213e"; // Cosmic blue
        if (level <= 15) return "#0f3460"; // Sacred blue
        if (level <= 20) return "#533a7b"; // Consciousness purple
        return "#ffd700"; // Enlightenment gold
    }
    
    // Business Integration
    public boolean allowsBusinessTrackAdvancement() {
        return level >= 5; // Minimum SCL 5 for business development
    }
    
    public boolean allowsLeadershipRoles() {
        return level >= 13; // SCL 13+ for leadership positions
    }
    
    public boolean allowsStrategicContributions() {
        return level >= 15; // SCL 15+ for strategic input
    }
    
    public boolean allowsPlatformGovernance() {
        return level >= 20; // SCL 20+ for platform governance
    }
    
    // Consciousness Categories
    public ConsciousnessCategory getCategory() {
        if (level <= 5) return ConsciousnessCategory.AWAKENING;
        if (level <= 10) return ConsciousnessCategory.DEVELOPING;
        if (level <= 15) return ConsciousnessCategory.CONTRIBUTING;
        if (level <= 20) return ConsciousnessCategory.LEADING;
        return ConsciousnessCategory.TRANSCENDING;
    }
    
    public enum ConsciousnessCategory {
        AWAKENING("Bewusstseins-Erwachen", "Grundlegende Platform-Nutzung"),
        DEVELOPING("Bewusstseins-Entwicklung", "Skill-Entwicklung und Community-Aufbau"),
        CONTRIBUTING("Bewusstseins-Beitrag", "Aktive Platform-Contributions"),
        LEADING("Bewusstseins-Führung", "Leadership und Team-Coordination"),
        TRANSCENDING("Bewusstseins-Transzendenz", "Planetare Transformation");
        
        private final String displayName;
        private final String description;
        
        ConsciousnessCategory(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
    
    // Helper class for level definitions
    private static class SCLDefinition {
        final int level;
        final String name;
        final String description;
        final int requiredContributions;
        final int fibonacciIndex;
        
        SCLDefinition(int level, String name, String description, int requiredContributions, int fibonacciIndex) {
            this.level = level;
            this.name = name;
            this.description = description;
            this.requiredContributions = requiredContributions;
            this.fibonacciIndex = fibonacciIndex;
        }
    }
    
    // Getters and Setters
    public int getLevel() { return level; }
    
    public String getLevelName() { return levelName; }
    public void setLevelName(String levelName) { this.levelName = levelName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getRequiredContributions() { return requiredContributions; }
    public void setRequiredContributions(int requiredContributions) { this.requiredContributions = requiredContributions; }
    
    public int getFibonacciIndex() { return fibonacciIndex; }
    public void setFibonacciIndex(int fibonacciIndex) { this.fibonacciIndex = fibonacciIndex; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsciousnessLevel)) return false;
        ConsciousnessLevel that = (ConsciousnessLevel) o;
        return level == that.level;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(level);
    }
    
    @Override
    public String toString() {
        return "SCL " + level + " - " + levelName + " (" + requiredContributions + " contributions)";
    }
}
