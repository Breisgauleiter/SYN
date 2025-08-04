package org.syntopia.consciousness.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.syntopia.consciousness.platform.domain.*;
import org.syntopia.consciousness.platform.dto.ContributionCreateRequest;
import org.syntopia.consciousness.platform.dto.GitHubIssueDto;
import org.syntopia.consciousness.platform.dto.SCLProgressDto;
import org.syntopia.consciousness.platform.repository.SynContributionRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Sacred Contributing System Service
 * 
 * Core service for GitHub-integrated consciousness development through
 * sacred quests, SCL progression tracking, and business track advancement.
 * Transforms software development into spiritual practice.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
@Service
@Transactional
public class SacredContributingService {

    @Autowired
    private SynContributionRepository contributionRepository;

    @Autowired
    private UserService userService;

    /**
     * Transform GitHub Issue into Sacred Quest
     */
    public SynContribution transformIssueToQuest(GitHubIssueDto issue, SyntopiaUser user) {
        SynContribution quest = new SynContribution();
        
        // Sacred Quest Title Enhancement
        quest.setTitle(generateSacredTitle(issue));
        quest.setDescription(enhanceWithSacredContext(issue.getDescription()));
        
        // Quest Type & Business Track Analysis
        quest.setQuestType(determineQuestType(issue));
        quest.setBusinessTrack(determineBusinessTrack(issue));
        
        // Difficulty & SCL Requirement Calculation
        quest.setDifficultyLevel(calculateDifficulty(issue));
        quest.setExperiencePoints(calculateExperiencePoints(issue));
        quest.setEstimatedHours(issue.getEstimatedHours() != null ? issue.getEstimatedHours() : estimateHours(issue));
        
        // GitHub Integration Linking
        quest.linkToGitHubIssue(issue.getRepository(), issue.getIssueNumber(), issue.getGithubUrl());
        
        // User Assignment
        quest.setUser(user);
        quest.setStatus(ContributionStatus.PROPOSED);
        
        return contributionRepository.save(quest);
    }

    /**
     * Calculate comprehensive SCL progression for user
     */
    public SCLProgressDto calculateSCLProgress(String userId) {
        SyntopiaUser user = userService.findById(userId);
        List<SynContribution> userContributions = contributionRepository.findByUserId(userId);
        
        SCLProgressDto progress = new SCLProgressDto();
        
        // Current SCL State
        Integer currentSCL = userService.getCurrentSCLLevel(user);
        progress.setCurrentSCLLevel(currentSCL);
        progress.setCurrentSCLName(getSCLName(currentSCL));
        progress.setCurrentSCLDescription(getSCLDescription(currentSCL));
        
        // Experience Points Calculation
        double totalXP = userContributions.stream()
                .filter(c -> c.getStatus() == ContributionStatus.COMPLETED)
                .mapToDouble(SynContribution::getExperiencePoints)
                .sum();
        progress.setCurrentExperiencePoints(totalXP);
        
        // Quest Statistics
        int completedQuests = (int) userContributions.stream()
                .filter(c -> c.getStatus() == ContributionStatus.COMPLETED)
                .count();
        progress.setTotalQuestsCompleted(completedQuests);
        
        return progress;
    }

    /**
     * Track new GitHub contribution
     */
    public SynContribution trackGitHubContribution(ContributionCreateRequest contributionRequest, SyntopiaUser user) {
        SynContribution contribution = new SynContribution();
        
        contribution.setTitle(contributionRequest.getTitle());
        contribution.setDescription(contributionRequest.getDescription());
        contribution.setQuestType(contributionRequest.getQuestType());
        contribution.setBusinessTrack(contributionRequest.getBusinessTrack());
        contribution.setDifficultyLevel(contributionRequest.getDifficultyLevel());
        contribution.setEstimatedHours(contributionRequest.getEstimatedHours());
        contribution.setUser(user);
        
        // GitHub Integration
        if (contributionRequest.getGithubRepository() != null) {
            contribution.linkToGitHubIssue(
                contributionRequest.getGithubRepository(),
                contributionRequest.getGithubIssueNumber(),
                contributionRequest.getGithubUrl()
            );
        }
        
        contribution.setStatus(ContributionStatus.PROPOSED);
        return contributionRepository.save(contribution);
    }

    /**
     * Get personalized quest recommendations
     */
    public List<SynContribution> getPersonalizedQuests(SyntopiaUser user) {
        Integer currentSCL = userService.getCurrentSCLLevel(user);
        List<SynContribution> availableQuests = contributionRepository.findAvailableQuestsForUser(
            currentSCL + 2, // Allow slightly challenging quests
            user.getPrimaryBusinessTrack()
        );
        
        return availableQuests.stream()
                .filter(quest -> isQuestAppropriateForUser(quest, user))
                .limit(10) // Top 10 recommendations
                .collect(Collectors.toList());
    }

    /**
     * Start working on a Sacred Quest
     */
    public SynContribution startQuest(String questId, SyntopiaUser user) {
        SynContribution quest = contributionRepository.findById(questId)
                .orElseThrow(() -> new RuntimeException("Quest not found: " + questId));
        
        quest.startContribution();
        return contributionRepository.save(quest);
    }

    /**
     * Complete a Sacred Quest
     */
    public SynContribution completeQuest(String questId, SyntopiaUser user) {
        SynContribution quest = contributionRepository.findById(questId)
                .orElseThrow(() -> new RuntimeException("Quest not found: " + questId));
        
        quest.completeContribution(quest.getEstimatedHours());
        return contributionRepository.save(quest);
    }

    /**
     * Get user's contribution history
     */
    public List<SynContribution> getContributionHistory(SyntopiaUser user) {
        return contributionRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    /**
     * Sync with user's GitHub repositories
     */
    public String syncWithGitHub(SyntopiaUser user) {
        return "GitHub synchronization initiated for user: " + user.getEmail() + 
               ". This feature will be available after GitHub OAuth integration.";
    }

    // Private Helper Methods

    private String generateSacredTitle(GitHubIssueDto issue) {
        String originalTitle = issue.getTitle();
        List<String> labels = issue.getLabels() != null ? issue.getLabels() : new ArrayList<>();
        
        if (labels.contains("bug")) {
            return "🔧 Sacred Debugging Quest: " + originalTitle;
        } else if (labels.contains("feature") || labels.contains("enhancement")) {
            return "✨ Manifestation Quest: " + originalTitle;
        } else if (labels.contains("documentation")) {
            return "📜 Knowledge Crystallization: " + originalTitle;
        } else {
            return "🎯 Consciousness Expansion: " + originalTitle;
        }
    }

    private String enhanceWithSacredContext(String description) {
        if (description == null) return "";
        
        return description + "\n\n" +
               "🌟 Sacred Development Intention:\n" +
               "This quest is an opportunity for consciousness evolution through sacred technology practice.";
    }

    private QuestType determineQuestType(GitHubIssueDto issue) {
        List<String> labels = issue.getLabels() != null ? issue.getLabels() : new ArrayList<>();
        
        if (labels.contains("community") || labels.contains("documentation")) {
            return QuestType.COMMUNITY_QUEST;
        } else if (labels.contains("leadership")) {
            return QuestType.LEADERSHIP_QUEST;
        } else {
            return QuestType.PLATFORM_QUEST;
        }
    }

    private BusinessTrack determineBusinessTrack(GitHubIssueDto issue) {
        List<String> labels = issue.getLabels() != null ? issue.getLabels() : new ArrayList<>();
        String title = issue.getTitle().toLowerCase();
        
        if (labels.contains("frontend") || title.contains("ui") || title.contains("vue")) {
            return BusinessTrack.TECH_DEVELOPER;
        } else if (labels.contains("design") || title.contains("ux")) {
            return BusinessTrack.UX_DESIGNER;
        } else if (labels.contains("business")) {
            return BusinessTrack.BUSINESS_DEVELOPER;
        } else {
            return BusinessTrack.TECH_DEVELOPER; // Default
        }
    }

    private Integer calculateDifficulty(GitHubIssueDto issue) {
        int difficulty = 1;
        List<String> labels = issue.getLabels() != null ? issue.getLabels() : new ArrayList<>();
        
        if (labels.contains("good first issue")) difficulty = 1;
        else if (labels.contains("easy")) difficulty = 2;
        else if (labels.contains("medium")) difficulty = 4;
        else if (labels.contains("hard")) difficulty = 7;
        else if (labels.contains("epic")) difficulty = 10;
        
        return Math.min(10, Math.max(1, difficulty));
    }

    private Integer calculateExperiencePoints(GitHubIssueDto issue) {
        int difficulty = calculateDifficulty(issue);
        int[] fibonacciXP = {10, 15, 25, 40, 65, 105, 170, 275, 445, 720};
        return fibonacciXP[Math.min(difficulty - 1, fibonacciXP.length - 1)];
    }

    private Double estimateHours(GitHubIssueDto issue) {
        int difficulty = calculateDifficulty(issue);
        double[] fibonacciHours = {0.5, 1.0, 1.5, 2.5, 4.0, 6.5, 10.5, 17.0, 27.5, 44.5};
        return fibonacciHours[Math.min(difficulty - 1, fibonacciHours.length - 1)];
    }

    private String getSCLName(Integer level) {
        Map<Integer, String> sclNames = Map.of(
            1, "Sacred Seeker",
            2, "Code Mystic", 
            3, "Sacred Collaborator",
            4, "Consciousness Builder",
            5, "Sacred Mentor"
        );
        return sclNames.getOrDefault(level, "Sacred Practitioner Level " + level);
    }

    private String getSCLDescription(Integer level) {
        Map<Integer, String> sclDescriptions = Map.of(
            1, "Beginning sacred consciousness + technology journey",
            2, "Understanding sacred technology principles through practice",
            3, "Sacred teamwork + peer-to-peer consciousness development", 
            4, "Building consciousness-raising technology",
            5, "Mentoring others in sacred development practices"
        );
        return sclDescriptions.getOrDefault(level, "Advanced sacred consciousness practitioner");
    }

    private boolean isQuestAppropriateForUser(SynContribution quest, SyntopiaUser user) {
        Integer currentSCL = userService.getCurrentSCLLevel(user);
        return quest.getDifficultyLevel() <= currentSCL + 2;
    }
}
