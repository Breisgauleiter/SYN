package org.syntopia.consciousness.platform.domain;

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
