package org.syntopia.consciousness.platform.domain;

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
