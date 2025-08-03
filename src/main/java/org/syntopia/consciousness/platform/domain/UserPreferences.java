package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

/**
 * User Preferences for Sacred Consciousness Platform Experience
 * 
 * Controls how users experience the platform across different areas,
 * from fully gamified to purely professional modes.
 */
@Embeddable
public class UserPreferences {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gamification_preference")
    private GamificationPreference gamificationPreference;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sacred_geometry_theme")
    private SacredGeometryTheme sacredGeometryTheme;
    
    @Column(name = "language_code")
    private String languageCode; // DE, EN, ES, FR, etc.
    
    @Column(name = "enable_consciousness_metrics")
    private boolean enableConsciousnessMetrics;
    
    @Column(name = "enable_sacred_timing")
    private boolean enableSacredTiming;
    
    @Column(name = "enable_synchronicity_notifications")
    private boolean enableSynchronicityNotifications;
    
    @Column(name = "professional_mode_hours")
    private String professionalModeHours; // e.g., "09:00-17:00" for business hours
    
    @Column(name = "enable_gene_keys_insights")
    private boolean enableGeneKeysInsights;
    
    @Column(name = "privacy_level")
    private int privacyLevel; // 1-5, higher = more privacy
    
    // Constructors
    public UserPreferences() {
        // Sacred defaults for consciousness evolution
        this.gamificationPreference = GamificationPreference.MODERATE;
        this.sacredGeometryTheme = SacredGeometryTheme.COSMIC_DEEP_SPACE;
        this.languageCode = "DE"; // Default to German for SYNtopia
        this.enableConsciousnessMetrics = true;
        this.enableSacredTiming = true;
        this.enableSynchronicityNotifications = true;
        this.enableGeneKeysInsights = true;
        this.privacyLevel = 3; // Balanced privacy
    }
    
    // Sacred Experience Methods
    public boolean shouldShowGamificationIn(PlatformArea area) {
        switch (gamificationPreference) {
            case DISABLED:
                return false;
            case PROFESSIONAL:
                return area.getType() == PlatformAreaType.FULLY_GAMIFIED;
            case MINIMAL:
                return area.getType() == PlatformAreaType.FULLY_GAMIFIED ||
                       area.getType() == PlatformAreaType.PARTIALLY_GAMIFIED;
            case MODERATE:
                return area.getType() != PlatformAreaType.NON_GAMIFIED;
            case MAXIMUM:
                return true;
            default:
                return false;
        }
    }
    
    public boolean isInProfessionalModeTime() {
        if (professionalModeHours == null || professionalModeHours.isEmpty()) {
            return false;
        }
        
        // TODO: Implement time-based professional mode detection
        // For now, simple check
        java.time.LocalTime now = java.time.LocalTime.now();
        return now.getHour() >= 9 && now.getHour() < 17;
    }
    
    public boolean allowsDataCollection() {
        return privacyLevel <= 3; // Levels 1-3 allow some data collection
    }
    
    public boolean allowsAIRecommendations() {
        return privacyLevel <= 4 && enableGeneKeysInsights;
    }
    
    // Sacred Geometry Experience
    public String getPrimaryColor() {
        return sacredGeometryTheme.getPrimaryColor();
    }
    
    public String getAccentColor() {
        return sacredGeometryTheme.getAccentColor();
    }
    
    // Getters and Setters
    public GamificationPreference getGamificationPreference() { return gamificationPreference; }
    public void setGamificationPreference(GamificationPreference gamificationPreference) { 
        this.gamificationPreference = gamificationPreference; 
    }
    
    public SacredGeometryTheme getSacredGeometryTheme() { return sacredGeometryTheme; }
    public void setSacredGeometryTheme(SacredGeometryTheme sacredGeometryTheme) { 
        this.sacredGeometryTheme = sacredGeometryTheme; 
    }
    
    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
    
    public boolean isEnableConsciousnessMetrics() { return enableConsciousnessMetrics; }
    public void setEnableConsciousnessMetrics(boolean enableConsciousnessMetrics) { 
        this.enableConsciousnessMetrics = enableConsciousnessMetrics; 
    }
    
    public boolean isEnableSacredTiming() { return enableSacredTiming; }
    public void setEnableSacredTiming(boolean enableSacredTiming) { 
        this.enableSacredTiming = enableSacredTiming; 
    }
    
    public boolean isEnableSynchronicityNotifications() { return enableSynchronicityNotifications; }
    public void setEnableSynchronicityNotifications(boolean enableSynchronicityNotifications) { 
        this.enableSynchronicityNotifications = enableSynchronicityNotifications; 
    }
    
    public String getProfessionalModeHours() { return professionalModeHours; }
    public void setProfessionalModeHours(String professionalModeHours) { 
        this.professionalModeHours = professionalModeHours; 
    }
    
    public boolean isEnableGeneKeysInsights() { return enableGeneKeysInsights; }
    public void setEnableGeneKeysInsights(boolean enableGeneKeysInsights) { 
        this.enableGeneKeysInsights = enableGeneKeysInsights; 
    }
    
    public int getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(int privacyLevel) { 
        if (privacyLevel < 1 || privacyLevel > 5) {
            throw new IllegalArgumentException("Privacy level must be between 1 and 5");
        }
        this.privacyLevel = privacyLevel; 
    }
    
    @Override
    public String toString() {
        return "UserPreferences{" +
                "gamification=" + gamificationPreference +
                ", theme=" + sacredGeometryTheme +
                ", language='" + languageCode + '\'' +
                ", privacyLevel=" + privacyLevel +
                '}';
    }
}
