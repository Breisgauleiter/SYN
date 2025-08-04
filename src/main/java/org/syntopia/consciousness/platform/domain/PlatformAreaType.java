package org.syntopia.consciousness.platform.domain;

/**
 * Platform Area Types for Experience Control
 */
public enum PlatformAreaType {
    FULLY_GAMIFIED("Fully Gamified", "Complete gaming experience with quests, achievements, and progression"),
    PARTIALLY_GAMIFIED("Partially Gamified", "Some gaming elements with option to disable"),
    NON_GAMIFIED("Non-Gamified", "Pure functional interface without gaming elements"),
    ADAPTIVE_GAMIFICATION("Adaptive Gamification", "User-controlled gamification level"),
    SELECTIVELY_GAMIFIED("Selectively Gamified", "Context-aware gamification");
    
    private final String displayName;
    private final String description;
    
    PlatformAreaType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
