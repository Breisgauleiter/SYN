package org.syntopia.consciousness.platform.domain;

/**
 * SYN Contribution Level (SCL) System - Consciousness-Based Progression
 * 
 * 25-level consciousness evolution system based on:
 * - Fibonacci progression for natural growth patterns
 * - Sacred geometry principles  
 * - Meaningful contribution requirements
 * - Business track advancement
 */
public enum BusinessTrack {
    
    // Tech Development Track
    TECH_DEVELOPER("Tech Developer", "Platform development and technical innovation", 
                   new String[]{"Java", "Spring Boot", "OrientDB", "Vue.js", "GraphQL"}),
    
    // Business Development Track  
    BUSINESS_DEVELOPER("Business Developer", "Strategic partnerships and growth",
                       new String[]{"Sales", "Partnerships", "Strategy", "Market Analysis", "Networking"}),
    
    // Design & User Experience Track
    UX_DESIGNER("UX Designer", "Sacred geometry design and user experience",
                new String[]{"UI/UX", "Sacred Geometry", "P5.js", "Figma", "User Research"}),
    
    // Data Science & Analytics Track
    DATA_SCIENTIST("Data Scientist", "Consciousness analytics and ethical AI",
                   new String[]{"Python", "Machine Learning", "Ethics", "Statistics", "Data Visualization"}),
    
    // Legal & Compliance Track
    LEGAL_ADVISOR("Legal Advisor", "Privacy-first legal framework and compliance",
                  new String[]{"GDPR", "Privacy Law", "Open Source", "DID", "Data Sovereignty"}),
    
    // Finance & Operations Track
    FINANCE_ANALYST("Finance Analyst", "Sacred economics and sustainable finance",
                    new String[]{"Finance", "Economics", "Sustainability", "Investment", "Operations"}),
    
    // Sustainability & Impact Track
    SUSTAINABILITY_LEAD("Sustainability Lead", "Planetary consciousness and ecological impact",
                        new String[]{"Sustainability", "Ecology", "Impact Measurement", "ESG", "Regeneration"});
    
    private final String displayName;
    private final String description;
    private final String[] coreSkills;
    
    BusinessTrack(String displayName, String description, String[] coreSkills) {
        this.displayName = displayName;
        this.description = description;
        this.coreSkills = coreSkills;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public String[] getCoreSkills() { return coreSkills; }
    
    public boolean isCompatibleWithGeneKey(GeneKey geneKey) {
        if (geneKey == null) return false;
        
        // Map Gene Keys to Business Tracks based on archetypal patterns
        switch (this) {
            case TECH_DEVELOPER:
                return isTechCompatible(geneKey.getKeyNumber());
            case BUSINESS_DEVELOPER:
                return isBusinessCompatible(geneKey.getKeyNumber());
            case UX_DESIGNER:
                return isDesignCompatible(geneKey.getKeyNumber());
            case DATA_SCIENTIST:
                return isAnalyticsCompatible(geneKey.getKeyNumber());
            case LEGAL_ADVISOR:
                return isLegalCompatible(geneKey.getKeyNumber());
            case FINANCE_ANALYST:
                return isFinanceCompatible(geneKey.getKeyNumber());
            case SUSTAINABILITY_LEAD:
                return isSustainabilityCompatible(geneKey.getKeyNumber());
            default:
                return false;
        }
    }
    
    private boolean isTechCompatible(int keyNumber) {
        // Gene Keys associated with innovation, systems, and technical mastery
        int[] techKeys = {1, 11, 21, 24, 34, 43, 49, 55, 62}; // Creativity, Ideas, Control, Innovation, etc.
        return contains(techKeys, keyNumber);
    }
    
    private boolean isBusinessCompatible(int keyNumber) {
        // Gene Keys associated with leadership, communication, and strategy
        int[] businessKeys = {7, 16, 31, 45, 54, 58, 60, 61, 63}; // Leadership, Enthusiasm, Influence, etc.
        return contains(businessKeys, keyNumber);
    }
    
    private boolean isDesignCompatible(int keyNumber) {
        // Gene Keys associated with beauty, aesthetics, and user experience
        int[] designKeys = {1, 14, 30, 37, 56, 64}; // Beauty, Possession, Recognition, Family, Stimulation, Confusion
        return contains(designKeys, keyNumber);
    }
    
    private boolean isAnalyticsCompatible(int keyNumber) {
        // Gene Keys associated with investigation, analysis, and patterns
        int[] analyticsKeys = {4, 17, 20, 38, 48, 57, 61}; // Answers, Following, Now, Opposition, Depth, Intuition, Mystery
        return contains(analyticsKeys, keyNumber);
    }
    
    private boolean isLegalCompatible(int keyNumber) {
        // Gene Keys associated with justice, structure, and protection
        int[] legalKeys = {6, 18, 32, 40, 50, 51}; // Conflict, Correction, Endurance, Aloneness, Values, Shock
        return contains(legalKeys, keyNumber);
    }
    
    private boolean isFinanceCompatible(int keyNumber) {
        // Gene Keys associated with resources, abundance, and material mastery
        int[] financeKeys = {14, 19, 26, 45, 54, 55}; // Possession, Wanting, Trickster, Gathering, Ascension, Spirit
        return contains(financeKeys, keyNumber);
    }
    
    private boolean isSustainabilityCompatible(int keyNumber) {
        // Gene Keys associated with service, ecology, and planetary consciousness
        int[] sustainabilityKeys = {2, 8, 15, 25, 46, 52, 58}; // Love, Contribution, Extremes, Innocence, Seriousness, Stillness, Vitality
        return contains(sustainabilityKeys, keyNumber);
    }
    
    private boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) return true;
        }
        return false;
    }
    
    public static BusinessTrack getRecommendedTrack(HologeneticProfile profile) {
        if (profile == null || !profile.isComplete()) return null;
        
        GeneKey lifeworkKey = profile.getLifeworkGeneKey();
        if (lifeworkKey == null) return null;
        
        // Find the most compatible business track based on lifework Gene Key
        for (BusinessTrack track : values()) {
            if (track.isCompatibleWithGeneKey(lifeworkKey)) {
                return track;
            }
        }
        
        // Default to tech developer if no specific match
        return TECH_DEVELOPER;
    }
}
