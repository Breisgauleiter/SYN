package org.syntopia.consciousness.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.BusinessTrack;
import org.syntopia.consciousness.platform.domain.ContributionStatus;
import org.syntopia.consciousness.platform.domain.SynContribution;

import java.util.List;
import java.util.Optional;

/**
 * Sacred Contribution Repository
 * 
 * Data access layer for Sacred Quests and GitHub-integrated contributions.
 * Provides consciousness-aware queries for quest recommendation, SCL progression
 * tracking, and sacred team formation.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
@Repository
public interface SynContributionRepository extends JpaRepository<SynContribution, String> {

    /**
     * Find all contributions by user ID
     */
    List<SynContribution> findByUserId(String userId);

    /**
     * Find contributions by user ID ordered by creation date (newest first)
     */
    List<SynContribution> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find available quests for user based on SCL level and business track
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.status = 'PROPOSED' AND " +
           "c.difficultyLevel <= :maxDifficulty AND " +
           "(:businessTrack IS NULL OR c.businessTrack = :businessTrack)")
    List<SynContribution> findAvailableQuestsForUser(
            @Param("maxDifficulty") Integer maxDifficulty,
            @Param("businessTrack") BusinessTrack businessTrack);

    /**
     * Find contributions by business track and status
     */
    List<SynContribution> findByBusinessTrackAndStatus(BusinessTrack businessTrack, ContributionStatus status);

    /**
     * Find completed contributions by user for XP calculation
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.status = 'COMPLETED'")
    List<SynContribution> findCompletedContributionsByUser(@Param("userId") String userId);

    /**
     * Find contributions linked to GitHub issues
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.githubIssueNumber IS NOT NULL AND " +
           "c.githubRepository IS NOT NULL")
    List<SynContribution> findGitHubLinkedContributions();

    /**
     * Find contributions by GitHub repository
     */
    List<SynContribution> findByGithubRepository(String githubRepository);

    /**
     * Find contribution by GitHub issue number and repository
     */
    Optional<SynContribution> findByGithubRepositoryAndGithubIssueNumber(
            String githubRepository, Integer githubIssueNumber);

    /**
     * Count completed quests by user
     */
    @Query("SELECT COUNT(c) FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.status = 'COMPLETED'")
    Long countCompletedQuestsByUser(@Param("userId") String userId);

    /**
     * Calculate total experience points for user
     */
    @Query("SELECT COALESCE(SUM(c.experiencePoints), 0) FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.status = 'COMPLETED'")
    Double sumExperiencePointsByUser(@Param("userId") String userId);

    /**
     * Find recent contributions for growth rate calculation
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.status = 'COMPLETED' AND " +
           "c.completedAt >= :since")
    List<SynContribution> findRecentCompletedContributions(
            @Param("userId") String userId,
            @Param("since") java.time.LocalDateTime since);

    /**
     * Find collaborative quests (multiple contributors)
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.status = 'PROPOSED' AND " +
           "c.estimatedHours > 8.0") // Larger quests likely collaborative
    List<SynContribution> findCollaborativeQuests();

    /**
     * Find quests by difficulty level range
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.status = 'PROPOSED' AND " +
           "c.difficultyLevel BETWEEN :minDifficulty AND :maxDifficulty")
    List<SynContribution> findQuestsByDifficultyRange(
            @Param("minDifficulty") Integer minDifficulty,
            @Param("maxDifficulty") Integer maxDifficulty);

    /**
     * Find high-value quests (high XP rewards)
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.status = 'PROPOSED' AND " +
           "c.experiencePoints >= :minXP " +
           "ORDER BY c.experiencePoints DESC")
    List<SynContribution> findHighValueQuests(@Param("minXP") Integer minXP);

    /**
     * Find active quests (in progress) by user
     */
    @Query("SELECT c FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.status IN ('IN_PROGRESS', 'REVIEW')")
    List<SynContribution> findActiveQuestsByUser(@Param("userId") String userId);

    /**
     * Count contributions by business track for user
     */
    @Query("SELECT COUNT(c) FROM SynContribution c WHERE " +
           "c.user.id = :userId AND " +
           "c.businessTrack = :businessTrack AND " +
           "c.status = 'COMPLETED'")
    Long countContributionsByUserAndTrack(
            @Param("userId") String userId,
            @Param("businessTrack") BusinessTrack businessTrack);
}
