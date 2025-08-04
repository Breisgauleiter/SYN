package org.syntopia.consciousness.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.syntopia.consciousness.platform.domain.BusinessTrack;
import org.syntopia.consciousness.platform.domain.QuestType;

import java.util.List;

/**
 * Contribution Creation Request Data Transfer Object
 * 
 * Request object for creating new contributions from various sources including
 * GitHub commits, pull requests, code reviews, and manual quest creation.
 * Supports consciousness-aware categorization and automatic SCL progression.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
public class ContributionCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Quest type is required")
    private QuestType questType;

    private BusinessTrack businessTrack;

    private Integer difficultyLevel; // 1-10 scale

    private Double estimatedHours;
    private List<String> requiredSkills;

    // GitHub Integration Fields
    private String githubRepository;
    private Integer githubIssueNumber;
    private String githubUrl;
    private String githubCommitHash;
    private String githubPullRequestUrl;

    // Consciousness Development Fields
    private Boolean isCollaborative;
    private Integer teamSize;
    private String mentorEmail; // If mentoring is involved
    private String consciousnessIntent; // User's conscious intention for this work
    private List<String> synchronicityNotes; // Meaningful coincidences during work

    // Constructors
    public ContributionCreateRequest() {}

    public ContributionCreateRequest(String title, QuestType questType) {
        this.title = title;
        this.questType = questType;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public QuestType getQuestType() { return questType; }
    public void setQuestType(QuestType questType) { this.questType = questType; }

    public BusinessTrack getBusinessTrack() { return businessTrack; }
    public void setBusinessTrack(BusinessTrack businessTrack) { this.businessTrack = businessTrack; }

    public Integer getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(Integer difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public Double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Double estimatedHours) { this.estimatedHours = estimatedHours; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getGithubRepository() { return githubRepository; }
    public void setGithubRepository(String githubRepository) { this.githubRepository = githubRepository; }

    public Integer getGithubIssueNumber() { return githubIssueNumber; }
    public void setGithubIssueNumber(Integer githubIssueNumber) { this.githubIssueNumber = githubIssueNumber; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getGithubCommitHash() { return githubCommitHash; }
    public void setGithubCommitHash(String githubCommitHash) { this.githubCommitHash = githubCommitHash; }

    public String getGithubPullRequestUrl() { return githubPullRequestUrl; }
    public void setGithubPullRequestUrl(String githubPullRequestUrl) { this.githubPullRequestUrl = githubPullRequestUrl; }

    public Boolean getIsCollaborative() { return isCollaborative; }
    public void setIsCollaborative(Boolean isCollaborative) { this.isCollaborative = isCollaborative; }

    public Integer getTeamSize() { return teamSize; }
    public void setTeamSize(Integer teamSize) { this.teamSize = teamSize; }

    public String getMentorEmail() { return mentorEmail; }
    public void setMentorEmail(String mentorEmail) { this.mentorEmail = mentorEmail; }

    public String getConsciousnessIntent() { return consciousnessIntent; }
    public void setConsciousnessIntent(String consciousnessIntent) { this.consciousnessIntent = consciousnessIntent; }

    public List<String> getSynchronicityNotes() { return synchronicityNotes; }
    public void setSynchronicityNotes(List<String> synchronicityNotes) { this.synchronicityNotes = synchronicityNotes; }

    @Override
    public String toString() {
        return "ContributionCreateRequest{" +
                "title='" + title + '\'' +
                ", questType=" + questType +
                ", businessTrack=" + businessTrack +
                ", difficultyLevel=" + difficultyLevel +
                ", githubRepository='" + githubRepository + '\'' +
                ", githubIssueNumber=" + githubIssueNumber +
                ", isCollaborative=" + isCollaborative +
                '}';
    }
}
