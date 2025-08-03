package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * SYN Contribution - Gamified Platform Development
 * 
 * Represents individual contributions to platform development,
 * community building, or business advancement. Integrates with
 * GitHub issues and business track quests.
 */
@Entity
@Table(name = "syn_contributions")
public class SynContribution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SyntopiaUser user;
    
    @Column(nullable = false)
    @NotBlank(message = "Contribution title is required")
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestType questType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContributionStatus status;
    
    @Enumerated(EnumType.STRING)
    private BusinessTrack businessTrack;
    
    // GitHub Integration
    @Column(name = "github_issue_number")
    private Integer githubIssueNumber;
    
    @Column(name = "github_repository")
    private String githubRepository;
    
    @Column(name = "github_url")
    private String githubUrl;
    
    // Gamification Metrics
    @Column(name = "experience_points")
    private int experiencePoints = 0;
    
    @Column(name = "difficulty_level")
    private int difficultyLevel = 1; // 1-10 scale
    
    @Column(name = "estimated_hours")
    private Double estimatedHours;
    
    @Column(name = "actual_hours")
    private Double actualHours;
    
    // Consciousness Integration
    @Column(name = "consciousness_impact_score")
    private Double consciousnessImpactScore;
    
    @Column(name = "synchronicity_factor")
    private Double synchronicityFactor;
    
    // Metadata
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime dueDate;
    
    @Version
    private Long version;
    
    // Constructors
    public SynContribution() {
        this.createdAt = LocalDateTime.now();
        this.status = ContributionStatus.PROPOSED;
    }
    
    public SynContribution(SyntopiaUser user, String title, QuestType questType) {
        this();
        this.user = user;
        this.title = title;
        this.questType = questType;
    }
    
    // Business Logic Methods
    public void startContribution() {
        if (status == ContributionStatus.PROPOSED) {
            this.status = ContributionStatus.IN_PROGRESS;
            this.startedAt = LocalDateTime.now();
        }
    }
    
    public void submitForReview() {
        if (status == ContributionStatus.IN_PROGRESS) {
            this.status = ContributionStatus.REVIEW;
        }
    }
    
    public void completeContribution(Double actualHours) {
        if (status == ContributionStatus.REVIEW || status == ContributionStatus.IN_PROGRESS) {
            this.status = ContributionStatus.COMPLETED;
            this.completedAt = LocalDateTime.now();
            this.actualHours = actualHours;
            calculateExperiencePoints();
        }
    }
    
    private void calculateExperiencePoints() {
        // Base XP calculation using Sacred Geometry principles
        int baseXP = difficultyLevel * 10;
        
        // Fibonacci bonus for difficulty
        int[] fibonacciBonus = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        if (difficultyLevel < fibonacciBonus.length) {
            baseXP += fibonacciBonus[difficultyLevel] * 5;
        }
        
        // Consciousness impact multiplier
        if (consciousnessImpactScore != null) {
            baseXP = (int) (baseXP * (1 + consciousnessImpactScore));
        }
        
        // Synchronicity bonus
        if (synchronicityFactor != null && synchronicityFactor > 0.8) {
            baseXP = (int) (baseXP * 1.618); // Golden ratio bonus for high synchronicity
        }
        
        this.experiencePoints = baseXP;
    }
    
    public boolean isOverdue() {
        return dueDate != null && LocalDateTime.now().isAfter(dueDate) && !isCompleted();
    }
    
    public boolean isCompleted() {
        return status == ContributionStatus.COMPLETED || status == ContributionStatus.ARCHIVED;
    }
    
    public boolean isActive() {
        return status == ContributionStatus.IN_PROGRESS || status == ContributionStatus.REVIEW;
    }
    
    // GitHub Integration Methods
    public void linkToGitHubIssue(String repository, int issueNumber, String url) {
        this.githubRepository = repository;
        this.githubIssueNumber = issueNumber;
        this.githubUrl = url;
    }
    
    public boolean isLinkedToGitHub() {
        return githubIssueNumber != null && githubRepository != null;
    }
    
    // Consciousness Methods
    public void calculateConsciousnessImpact() {
        // Calculate based on contribution type and business track
        double impact = 0.5; // Base impact
        
        switch (questType) {
            case PLATFORM_QUEST:
                impact += 0.3; // Direct platform improvement
                break;
            case COMMUNITY_QUEST:
                impact += 0.4; // Community building is high impact
                break;
            case LEADERSHIP_QUEST:
                impact += 0.5; // Leadership has highest impact
                break;
            case HYBRID_QUEST:
                impact += 0.4; // Cross-platform integration
                break;
        }
        
        // Business track multiplier
        if (businessTrack != null) {
            impact *= 1.2; // Business track contributions get bonus
        }
        
        this.consciousnessImpactScore = Math.min(impact, 2.0); // Cap at 2.0
    }
    
    public void calculateSynchronicityFactor() {
        // TODO: Implement synchronicity calculation based on:
        // - Timing patterns
        // - Gene Keys alignment
        // - Community resonance
        // - Cosmic timing (moon phases, etc.)
        
        this.synchronicityFactor = Math.random(); // Placeholder
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public SyntopiaUser getUser() { return user; }
    public void setUser(SyntopiaUser user) { this.user = user; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public QuestType getQuestType() { return questType; }
    public void setQuestType(QuestType questType) { this.questType = questType; }
    
    public ContributionStatus getStatus() { return status; }
    public void setStatus(ContributionStatus status) { this.status = status; }
    
    public BusinessTrack getBusinessTrack() { return businessTrack; }
    public void setBusinessTrack(BusinessTrack businessTrack) { this.businessTrack = businessTrack; }
    
    public Integer getGithubIssueNumber() { return githubIssueNumber; }
    public void setGithubIssueNumber(Integer githubIssueNumber) { this.githubIssueNumber = githubIssueNumber; }
    
    public String getGithubRepository() { return githubRepository; }
    public void setGithubRepository(String githubRepository) { this.githubRepository = githubRepository; }
    
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    
    public int getExperiencePoints() { return experiencePoints; }
    public void setExperiencePoints(int experiencePoints) { this.experiencePoints = experiencePoints; }
    
    public int getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    
    public Double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Double estimatedHours) { this.estimatedHours = estimatedHours; }
    
    public Double getActualHours() { return actualHours; }
    public void setActualHours(Double actualHours) { this.actualHours = actualHours; }
    
    public Double getConsciousnessImpactScore() { return consciousnessImpactScore; }
    public void setConsciousnessImpactScore(Double consciousnessImpactScore) { 
        this.consciousnessImpactScore = consciousnessImpactScore; 
    }
    
    public Double getSynchronicityFactor() { return synchronicityFactor; }
    public void setSynchronicityFactor(Double synchronicityFactor) { 
        this.synchronicityFactor = synchronicityFactor; 
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    
    @Override
    public String toString() {
        return "SynContribution{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", questType=" + questType +
                ", status=" + status +
                ", experiencePoints=" + experiencePoints +
                ", createdAt=" + createdAt +
                '}';
    }
}
