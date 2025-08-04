package org.syntopia.consciousness.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.syntopia.consciousness.platform.domain.SynContribution;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;
import org.syntopia.consciousness.platform.dto.ContributionCreateRequest;
import org.syntopia.consciousness.platform.dto.GitHubIssueDto;
import org.syntopia.consciousness.platform.dto.SCLProgressDto;
import org.syntopia.consciousness.platform.service.SacredContributingService;
import org.syntopia.consciousness.platform.service.UserService;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Sacred Contributing System REST Controller
 * 
 * Provides API endpoints for GitHub-integrated quest system, SCL progression,
 * and consciousness-based contribution tracking. Transforms GitHub Issues into
 * Sacred Quests and tracks consciousness evolution through code contributions.
 * 
 * Features:
 * - Bidirectional GitHub Issues ↔ Sacred Quests synchronization
 * - SCL (Sacred Consciousness Level) progression tracking
 * - Business Track specialization through GitHub workflow
 * - Real-time contribution quality assessment
 * - Sacred team formation via consciousness compatibility
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
@RestController
@RequestMapping("/api/v1/contributing")
@CrossOrigin(origins = {"http://localhost:3000", "https://syntopia.org"})
public class SacredContributingController {

    @Autowired
    private SacredContributingService contributingService;

    @Autowired
    private UserService userService;

    /**
     * Create a Sacred Quest from a GitHub Issue
     * 
     * Transforms GitHub Issues into consciousness-aware quests with appropriate
     * SCL requirements, business track categorization, and XP rewards.
     * 
     * @param issue GitHub Issue data transfer object
     * @param auth Authentication context for user identification
     * @return Created Sacred Quest with consciousness development milestones
     */
    @PostMapping("/quest/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SynContribution> createQuestFromIssue(
            @Valid @RequestBody GitHubIssueDto issue,
            Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        SynContribution quest = contributingService.transformIssueToQuest(issue, user);
        
        return ResponseEntity.ok(quest);
    }

    /**
     * Get Sacred Consciousness Level progression for a user
     * 
     * Returns detailed SCL progression tracking including:
     * - Current consciousness level and XP
     * - Business track advancement
     * - Sacred skill development
     * - Next level requirements
     * 
     * @param userId User identifier for progression tracking
     * @return SCL progression data with consciousness evolution metrics
     */
    @GetMapping("/scl/{userId}/progress")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SCLProgressDto> getSCLProgress(@PathVariable String userId) {
        
        SCLProgressDto progress = contributingService.calculateSCLProgress(userId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Track a new contribution from GitHub
     * 
     * Webhook endpoint for real-time contribution tracking. Analyzes code quality,
     * collaboration patterns, and consciousness development impact to update
     * SCL progression and business track advancement.
     * 
     * @param contribution GitHub contribution data (commit, PR, review, etc.)
     * @param auth Authentication context for contribution attribution
     * @return Contribution result with consciousness evolution impact
     */
    @PostMapping("/contribution/track")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SynContribution> trackContribution(
            @Valid @RequestBody ContributionCreateRequest contribution,
            Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        SynContribution result = contributingService.trackGitHubContribution(contribution, user);
        
        return ResponseEntity.ok(result);
    }

    /**
     * Get all Sacred Quests for authenticated user
     * 
     * Returns personalized quest list based on:
     * - Current SCL level and capabilities
     * - Business track specialization
     * - Gene Keys compatibility (when available)
     * - Sacred timing and synchronicity factors
     * 
     * @param auth Authentication context for personalized quest selection
     * @return List of available Sacred Quests for consciousness development
     */
    @GetMapping("/quests")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<SynContribution>> getPersonalizedQuests(Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        List<SynContribution> quests = contributingService.getPersonalizedQuests(user);
        
        return ResponseEntity.ok(quests);
    }

    /**
     * Start working on a Sacred Quest
     * 
     * Updates quest status to IN_PROGRESS and initializes consciousness tracking.
     * Links to GitHub Issue if available for real-time synchronization.
     * 
     * @param questId Sacred Quest identifier
     * @param auth Authentication context for quest assignment
     * @return Updated quest with IN_PROGRESS status and tracking enabled
     */
    @PostMapping("/quest/{questId}/start")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SynContribution> startQuest(
            @PathVariable String questId,
            Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        SynContribution quest = contributingService.startQuest(questId, user);
        
        return ResponseEntity.ok(quest);
    }

    /**
     * Complete a Sacred Quest
     * 
     * Finalizes quest completion, calculates consciousness evolution impact,
     * awards XP and sacred achievements, and updates business track progression.
     * 
     * @param questId Sacred Quest identifier
     * @param auth Authentication context for completion attribution
     * @return Completed quest with consciousness evolution rewards
     */
    @PostMapping("/quest/{questId}/complete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SynContribution> completeQuest(
            @PathVariable String questId,
            Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        SynContribution quest = contributingService.completeQuest(questId, user);
        
        return ResponseEntity.ok(quest);
    }

    /**
     * Get user's contribution history
     * 
     * Returns comprehensive contribution tracking including:
     * - Sacred Quest completions
     * - GitHub contribution statistics
     * - Consciousness evolution timeline
     * - Business track milestones
     * - Sacred achievements earned
     * 
     * @param auth Authentication context for personal history access
     * @return Complete contribution history with consciousness development tracking
     */
    @GetMapping("/history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<SynContribution>> getContributionHistory(Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        List<SynContribution> history = contributingService.getContributionHistory(user);
        
        return ResponseEntity.ok(history);
    }

    /**
     * Sync with GitHub repositories
     * 
     * Triggers synchronization with connected GitHub repositories to:
     * - Import new Issues as Sacred Quests
     * - Update quest progress from PR status
     * - Track contribution quality and consciousness impact
     * - Form sacred teams based on collaboration patterns
     * 
     * @param auth Authentication context for GitHub access
     * @return Synchronization result with updated quest count
     */
    @PostMapping("/github/sync")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> syncWithGitHub(Authentication auth) {
        
        SyntopiaUser user = userService.findByEmail(auth.getName());
        String result = contributingService.syncWithGitHub(user);
        
        return ResponseEntity.ok(result);
    }
}
