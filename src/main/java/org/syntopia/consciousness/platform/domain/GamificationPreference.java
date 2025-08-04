package org.syntopia.consciousness.platform.domain;

/**
 * User Preference Settings
 */
public enum GamificationPreference {
    MAXIMUM("Maximum Gaming", "Full gamification across all compatible areas"),
    MODERATE("Moderate Gaming", "Balanced approach with optional gaming elements"),
    MINIMAL("Minimal Gaming", "Gaming only in dedicated gaming areas"),
    PROFESSIONAL("Professional Mode", "Business-focused interface with minimal gaming"),
    DISABLED("Disabled", "No gaming elements anywhere");
    
    private final String displayName;
    private final String description;
    
    GamificationPreference(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
