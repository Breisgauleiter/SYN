package org.syntopia.consciousness.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import org.syntopia.consciousness.platform.domain.BusinessTrack;
import org.syntopia.consciousness.platform.domain.QuestType;

import java.util.List;

/**
 * GitHub Issue Data Transfer Object
 * 
 * Represents GitHub Issue data for transformation into Sacred Quests.
 * Contains all necessary information for consciousness-aware quest creation
 * including difficulty assessment, business track categorization, and
 * sacred synchronicity factors.
 * 
 * @author SYN Consciousness Platform  
 * @version 2.0
 * @since August 2025
 */
public class GitHubIssueDto {

    @NotNull(message = "Issue number is required")
    @Min(value = 1, message = "Issue number must be positive")
    private Integer issueNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Repository is required")
    private String repository;

    @NotBlank(message = "Repository owner is required")
    private String repositoryOwner;

    private String githubUrl;

    private List<String> labels;

    private String assignee;

    private String milestone;

    private String state; // open, closed

    private String priority; // low, medium, high, critical

    // Sacred Consciousness Enhancement Fields
    private QuestType suggestedQuestType;
    private BusinessTrack suggestedBusinessTrack;
    private Integer estimatedComplexity; // 1-10 scale for SCL requirement calculation
    private Double estimatedHours;
    private List<String> requiredSkills;
    private Boolean isCollaborative;
    private Integer recommendedTeamSize;

    // Constructors
    public GitHubIssueDto() {}

    public GitHubIssueDto(Integer issueNumber, String title, String description, 
                         String repository, String repositoryOwner) {
        this.issueNumber = issueNumber;
        this.title = title;
        this.description = description;
        this.repository = repository;
        this.repositoryOwner = repositoryOwner;
    }

    // Getters and Setters
    public Integer getIssueNumber() { return issueNumber; }
    public void setIssueNumber(Integer issueNumber) { this.issueNumber = issueNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRepository() { return repository; }
    public void setRepository(String repository) { this.repository = repository; }

    public String getRepositoryOwner() { return repositoryOwner; }
    public void setRepositoryOwner(String repositoryOwner) { this.repositoryOwner = repositoryOwner; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }

    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }

    public String getMilestone() { return milestone; }
    public void setMilestone(String milestone) { this.milestone = milestone; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public QuestType getSuggestedQuestType() { return suggestedQuestType; }
    public void setSuggestedQuestType(QuestType suggestedQuestType) { this.suggestedQuestType = suggestedQuestType; }

    public BusinessTrack getSuggestedBusinessTrack() { return suggestedBusinessTrack; }
    public void setSuggestedBusinessTrack(BusinessTrack suggestedBusinessTrack) { this.suggestedBusinessTrack = suggestedBusinessTrack; }

    public Integer getEstimatedComplexity() { return estimatedComplexity; }
    public void setEstimatedComplexity(Integer estimatedComplexity) { this.estimatedComplexity = estimatedComplexity; }

    public Double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Double estimatedHours) { this.estimatedHours = estimatedHours; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public Boolean getIsCollaborative() { return isCollaborative; }
    public void setIsCollaborative(Boolean isCollaborative) { this.isCollaborative = isCollaborative; }

    public Integer getRecommendedTeamSize() { return recommendedTeamSize; }
    public void setRecommendedTeamSize(Integer recommendedTeamSize) { this.recommendedTeamSize = recommendedTeamSize; }

    @Override
    public String toString() {
        return "GitHubIssueDto{" +
                "issueNumber=" + issueNumber +
                ", title='" + title + '\'' +
                ", repository='" + repository + '\'' +
                ", repositoryOwner='" + repositoryOwner + '\'' +
                ", state='" + state + '\'' +
                ", labels=" + labels +
                ", suggestedQuestType=" + suggestedQuestType +
                ", suggestedBusinessTrack=" + suggestedBusinessTrack +
                '}';
    }
}
