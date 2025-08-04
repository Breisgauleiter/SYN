package org.syntopia.consciousness.platform.domain;

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
