package org.syntopia.consciousness.platform.domain;

/**
 * Platform-wide enums and constants for SYNtopia 2.0
 */

/**
 * Quest Types for Gamified Business Development
 */
public enum QuestType {
    PLATFORM_QUEST("Platform Quest", "GitHub Issues converted to game quests"),
    COMMUNITY_QUEST("Community Quest", "Profile building and networking"),
    LEADERSHIP_QUEST("Leadership Quest", "Strategic planning and team coordination"),
    HYBRID_QUEST("Hybrid Quest", "Cross-platform integration challenges");
    
    private final String displayName;
    private final String description;
    
    QuestType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}

/**
 * SYN Contribution Status Tracking
 */
public enum ContributionStatus {
    PROPOSED("Proposed", "Initial idea or proposal"),
    IN_PROGRESS("In Progress", "Actively being worked on"),
    REVIEW("Under Review", "Awaiting review and approval"),
    COMPLETED("Completed", "Successfully finished and integrated"),
    ARCHIVED("Archived", "Completed but no longer active");
    
    private final String displayName;
    private final String description;
    
    ContributionStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}

/**
 * Platform Areas for Gamification Control
 */
public enum PlatformArea {
    // Fully Gamified Areas
    SYNTOPIA_GAMING_HUB("SYNtopia Gaming Hub", PlatformAreaType.FULLY_GAMIFIED),
    CONSCIOUSNESS_QUESTS("Consciousness Quests", PlatformAreaType.FULLY_GAMIFIED),
    SYN_TREE_ROADMAP("SYNTree Roadmap", PlatformAreaType.PARTIALLY_GAMIFIED),
    
    // Non-Gamified Areas  
    SYNMAP_GEOGRAPHICAL("SYNmap Geographical", PlatformAreaType.NON_GAMIFIED),
    SYNSHARE_MARKETPLACE("SYNshare Marketplace", PlatformAreaType.NON_GAMIFIED),
    BUSINESS_ANALYTICS("Business Analytics", PlatformAreaType.NON_GAMIFIED),
    
    // Hybrid Areas
    DASHBOARD("Dashboard", PlatformAreaType.ADAPTIVE_GAMIFICATION),
    USER_PROFILE("User Profile", PlatformAreaType.ADAPTIVE_GAMIFICATION),
    SYNVERSE_NETWORK("SYNverse Network", PlatformAreaType.SELECTIVELY_GAMIFIED);
    
    private final String displayName;
    private final PlatformAreaType type;
    
    PlatformArea(String displayName, PlatformAreaType type) {
        this.displayName = displayName;
        this.type = type;
    }
    
    public String getDisplayName() { return displayName; }
    public PlatformAreaType getType() { return type; }
}

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

/**
 * Sacred Geometry Themes
 */
public enum SacredGeometryTheme {
    COSMIC_DEEP_SPACE("Cosmic Deep Space", "#0a0a0f", "#1a1a2e", "Starfield with nebula"),
    CONSCIOUSNESS_PURPLE("Consciousness Purple", "#2d1b69", "#533a7b", "Sacred purple tones"),
    ENLIGHTENMENT_GOLD("Enlightenment Gold", "#f4d03f", "#ffd700", "Golden illumination"),
    NATURE_GREEN("Nature Green", "#27ae60", "#2ecc71", "Earth consciousness"),
    HEALING_BLUE("Healing Blue", "#3498db", "#85c1e9", "Oceanic tranquility");
    
    private final String displayName;
    private final String primaryColor;
    private final String accentColor;
    private final String description;
    
    SacredGeometryTheme(String displayName, String primaryColor, String accentColor, String description) {
        this.displayName = displayName;
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getPrimaryColor() { return primaryColor; }
    public String getAccentColor() { return accentColor; }
    public String getDescription() { return description; }
}
