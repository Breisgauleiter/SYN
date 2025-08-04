package org.syntopia.consciousness.platform.dto;

import org.syntopia.consciousness.platform.domain.BusinessTrack;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Sacred Consciousness Level Progress Data Transfer Object
 * 
 * Comprehensive consciousness evolution tracking including SCL progression,
 * business track advancement, sacred skill development, and next level
 * requirements for continuous growth guidance.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0  
 * @since August 2025
 */
public class SCLProgressDto {

    // Current Consciousness State
    private Integer currentSCLLevel;
    private String currentSCLName;
    private String currentSCLDescription;
    private Double currentExperiencePoints;
    private Double nextLevelRequiredXP;
    private Double progressToNextLevel; // 0.0 - 1.0

    // Business Track Progression
    private BusinessTrack primaryBusinessTrack;
    private List<BusinessTrack> secondaryBusinessTracks;
    private Map<BusinessTrack, Integer> trackLevels; // Track -> Level (0-5)
    private Map<BusinessTrack, Double> trackXP; // Track -> Experience Points

    // Sacred Skills Development
    private List<String> completedSkills;
    private List<String> inProgressSkills;
    private List<String> recommendedNextSkills;
    private Map<String, Integer> skillMastery; // Skill -> Mastery Level (0-100)

    // Quest & Contribution Statistics
    private Integer totalQuestsCompleted;
    private Integer questsCompletedThisMonth;
    private Integer gitHubContributions;
    private Integer totalCodeReviews;
    private Double averageQuestCompletionTime; // in hours

    // Sacred Achievements
    private List<String> unlockedAchievements;
    private List<String> nearbyAchievements; // Close to unlocking
    private Integer totalAchievementPoints;

    // Consciousness Evolution Metrics
    private Double consciousnessGrowthRate; // XP per week
    private LocalDateTime lastLevelUp;
    private LocalDateTime projectedNextLevelUp;
    private String consciousnessEvolutionTrend; // ACCELERATING, STEADY, SLOW

    // Next Level Requirements
    private List<String> nextLevelRequirements;
    private Integer questsRequiredForNextLevel;
    private List<String> skillsRequiredForNextLevel;
    private Double minConsciousnessScore; // For level gating

    // Community & Collaboration Metrics
    private Integer mentorshipHours;
    private Integer communitiesJoined;
    private Double teamCollaborationScore;
    private List<String> codonRingMemberships; // Gene Keys communities

    // Constructors
    public SCLProgressDto() {}

    public SCLProgressDto(Integer currentSCLLevel, String currentSCLName, 
                         Double currentExperiencePoints, BusinessTrack primaryBusinessTrack) {
        this.currentSCLLevel = currentSCLLevel;
        this.currentSCLName = currentSCLName;
        this.currentExperiencePoints = currentExperiencePoints;
        this.primaryBusinessTrack = primaryBusinessTrack;
    }

    // Business Logic Methods
    public boolean isReadyForNextLevel() {
        return progressToNextLevel >= 1.0;
    }

    public boolean canAccessSCLLevel(Integer level) {
        return currentSCLLevel >= level;
    }

    public Integer getQuestsUntilNextLevel() {
        if (questsRequiredForNextLevel != null && totalQuestsCompleted != null) {
            return Math.max(0, questsRequiredForNextLevel - totalQuestsCompleted);
        }
        return 0;
    }

    public Double getXPUntilNextLevel() {
        if (nextLevelRequiredXP != null && currentExperiencePoints != null) {
            return Math.max(0.0, nextLevelRequiredXP - currentExperiencePoints);
        }
        return 0.0;
    }

    // Getters and Setters
    public Integer getCurrentSCLLevel() { return currentSCLLevel; }
    public void setCurrentSCLLevel(Integer currentSCLLevel) { this.currentSCLLevel = currentSCLLevel; }

    public String getCurrentSCLName() { return currentSCLName; }
    public void setCurrentSCLName(String currentSCLName) { this.currentSCLName = currentSCLName; }

    public String getCurrentSCLDescription() { return currentSCLDescription; }
    public void setCurrentSCLDescription(String currentSCLDescription) { this.currentSCLDescription = currentSCLDescription; }

    public Double getCurrentExperiencePoints() { return currentExperiencePoints; }
    public void setCurrentExperiencePoints(Double currentExperiencePoints) { this.currentExperiencePoints = currentExperiencePoints; }

    public Double getNextLevelRequiredXP() { return nextLevelRequiredXP; }
    public void setNextLevelRequiredXP(Double nextLevelRequiredXP) { this.nextLevelRequiredXP = nextLevelRequiredXP; }

    public Double getProgressToNextLevel() { return progressToNextLevel; }
    public void setProgressToNextLevel(Double progressToNextLevel) { this.progressToNextLevel = progressToNextLevel; }

    public BusinessTrack getPrimaryBusinessTrack() { return primaryBusinessTrack; }
    public void setPrimaryBusinessTrack(BusinessTrack primaryBusinessTrack) { this.primaryBusinessTrack = primaryBusinessTrack; }

    public List<BusinessTrack> getSecondaryBusinessTracks() { return secondaryBusinessTracks; }
    public void setSecondaryBusinessTracks(List<BusinessTrack> secondaryBusinessTracks) { this.secondaryBusinessTracks = secondaryBusinessTracks; }

    public Map<BusinessTrack, Integer> getTrackLevels() { return trackLevels; }
    public void setTrackLevels(Map<BusinessTrack, Integer> trackLevels) { this.trackLevels = trackLevels; }

    public Map<BusinessTrack, Double> getTrackXP() { return trackXP; }
    public void setTrackXP(Map<BusinessTrack, Double> trackXP) { this.trackXP = trackXP; }

    public List<String> getCompletedSkills() { return completedSkills; }
    public void setCompletedSkills(List<String> completedSkills) { this.completedSkills = completedSkills; }

    public List<String> getInProgressSkills() { return inProgressSkills; }
    public void setInProgressSkills(List<String> inProgressSkills) { this.inProgressSkills = inProgressSkills; }

    public List<String> getRecommendedNextSkills() { return recommendedNextSkills; }
    public void setRecommendedNextSkills(List<String> recommendedNextSkills) { this.recommendedNextSkills = recommendedNextSkills; }

    public Map<String, Integer> getSkillMastery() { return skillMastery; }
    public void setSkillMastery(Map<String, Integer> skillMastery) { this.skillMastery = skillMastery; }

    public Integer getTotalQuestsCompleted() { return totalQuestsCompleted; }
    public void setTotalQuestsCompleted(Integer totalQuestsCompleted) { this.totalQuestsCompleted = totalQuestsCompleted; }

    public Integer getQuestsCompletedThisMonth() { return questsCompletedThisMonth; }
    public void setQuestsCompletedThisMonth(Integer questsCompletedThisMonth) { this.questsCompletedThisMonth = questsCompletedThisMonth; }

    public Integer getGitHubContributions() { return gitHubContributions; }
    public void setGitHubContributions(Integer gitHubContributions) { this.gitHubContributions = gitHubContributions; }

    public Integer getTotalCodeReviews() { return totalCodeReviews; }
    public void setTotalCodeReviews(Integer totalCodeReviews) { this.totalCodeReviews = totalCodeReviews; }

    public Double getAverageQuestCompletionTime() { return averageQuestCompletionTime; }
    public void setAverageQuestCompletionTime(Double averageQuestCompletionTime) { this.averageQuestCompletionTime = averageQuestCompletionTime; }

    public List<String> getUnlockedAchievements() { return unlockedAchievements; }
    public void setUnlockedAchievements(List<String> unlockedAchievements) { this.unlockedAchievements = unlockedAchievements; }

    public List<String> getNearbyAchievements() { return nearbyAchievements; }
    public void setNearbyAchievements(List<String> nearbyAchievements) { this.nearbyAchievements = nearbyAchievements; }

    public Integer getTotalAchievementPoints() { return totalAchievementPoints; }
    public void setTotalAchievementPoints(Integer totalAchievementPoints) { this.totalAchievementPoints = totalAchievementPoints; }

    public Double getConsciousnessGrowthRate() { return consciousnessGrowthRate; }
    public void setConsciousnessGrowthRate(Double consciousnessGrowthRate) { this.consciousnessGrowthRate = consciousnessGrowthRate; }

    public LocalDateTime getLastLevelUp() { return lastLevelUp; }
    public void setLastLevelUp(LocalDateTime lastLevelUp) { this.lastLevelUp = lastLevelUp; }

    public LocalDateTime getProjectedNextLevelUp() { return projectedNextLevelUp; }
    public void setProjectedNextLevelUp(LocalDateTime projectedNextLevelUp) { this.projectedNextLevelUp = projectedNextLevelUp; }

    public String getConsciousnessEvolutionTrend() { return consciousnessEvolutionTrend; }
    public void setConsciousnessEvolutionTrend(String consciousnessEvolutionTrend) { this.consciousnessEvolutionTrend = consciousnessEvolutionTrend; }

    public List<String> getNextLevelRequirements() { return nextLevelRequirements; }
    public void setNextLevelRequirements(List<String> nextLevelRequirements) { this.nextLevelRequirements = nextLevelRequirements; }

    public Integer getQuestsRequiredForNextLevel() { return questsRequiredForNextLevel; }
    public void setQuestsRequiredForNextLevel(Integer questsRequiredForNextLevel) { this.questsRequiredForNextLevel = questsRequiredForNextLevel; }

    public List<String> getSkillsRequiredForNextLevel() { return skillsRequiredForNextLevel; }
    public void setSkillsRequiredForNextLevel(List<String> skillsRequiredForNextLevel) { this.skillsRequiredForNextLevel = skillsRequiredForNextLevel; }

    public Double getMinConsciousnessScore() { return minConsciousnessScore; }
    public void setMinConsciousnessScore(Double minConsciousnessScore) { this.minConsciousnessScore = minConsciousnessScore; }

    public Integer getMentorshipHours() { return mentorshipHours; }
    public void setMentorshipHours(Integer mentorshipHours) { this.mentorshipHours = mentorshipHours; }

    public Integer getCommunitiesJoined() { return communitiesJoined; }
    public void setCommunitiesJoined(Integer communitiesJoined) { this.communitiesJoined = communitiesJoined; }

    public Double getTeamCollaborationScore() { return teamCollaborationScore; }
    public void setTeamCollaborationScore(Double teamCollaborationScore) { this.teamCollaborationScore = teamCollaborationScore; }

    public List<String> getCodonRingMemberships() { return codonRingMemberships; }
    public void setCodonRingMemberships(List<String> codonRingMemberships) { this.codonRingMemberships = codonRingMemberships; }

    @Override
    public String toString() {
        return "SCLProgressDto{" +
                "currentSCLLevel=" + currentSCLLevel +
                ", currentSCLName='" + currentSCLName + '\'' +
                ", currentExperiencePoints=" + currentExperiencePoints +
                ", primaryBusinessTrack=" + primaryBusinessTrack +
                ", totalQuestsCompleted=" + totalQuestsCompleted +
                ", progressToNextLevel=" + progressToNextLevel +
                '}';
    }
}
