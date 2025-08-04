package org.syntopia.consciousness.platform.repository.tao;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TAO User Repository - Objects Collection for SyntopiaUser
 * 
 * Implements Facebook's TAO (The Associations and Objects) pattern
 * for high-performance consciousness network queries.
 * 
 * @author SYNtopia Consciousness Collective
 * @since 2025-08-04
 */
@Repository
public interface TAOUserRepository extends ArangoRepository<SyntopiaUser, String> {

    // =================== TAO OBJECT QUERIES ===================
    
    /**
     * Find user by username (TAO Object lookup)
     */
    @Query("FOR user IN users FILTER user.username == @username RETURN user")
    Optional<SyntopiaUser> findByUsername(@Param("username") String username);
    
    /**
     * Find user by email (TAO Object lookup)
     */
    @Query("FOR user IN users FILTER user.email == @email RETURN user")
    Optional<SyntopiaUser> findByEmail(@Param("email") String email);
    
    /**
     * Find users by consciousness level range (TAO Object filtering)
     */
    @Query("""
        FOR user IN users 
            FILTER user.currentSCL >= @minSCL AND user.currentSCL <= @maxSCL
            SORT user.currentSCL DESC
            LIMIT @limit
            RETURN user
        """)
    List<SyntopiaUser> findByConsciousnessLevelRange(
        @Param("minSCL") Integer minSCL, 
        @Param("maxSCL") Integer maxSCL,
        @Param("limit") Integer limit
    );
    
    /**
     * Find users by Gene Key (TAO Object search)
     */
    @Query("""
        FOR user IN users
            FILTER @geneKey IN user.activatedGeneKeys
            SORT user.consciousnessScore DESC
            LIMIT @limit
            RETURN user
        """)
    List<SyntopiaUser> findByGeneKey(@Param("geneKey") String geneKey, @Param("limit") Integer limit);
    
    /**
     * Find users by Business Track (TAO Object filtering)
     */
    @Query("""
        FOR user IN users
            FILTER @businessTrack IN user.roles[*].name
            FILTER user.isGamefiedModeEnabled == true
            SORT user.lastActiveAt DESC
            LIMIT @limit
            RETURN user
        """)
    List<SyntopiaUser> findActiveUsersByBusinessTrack(
        @Param("businessTrack") String businessTrack,
        @Param("limit") Integer limit
    );
    
    /**
     * Find recently active users (TAO Object time-based query)
     */
    @Query("""
        FOR user IN users
            FILTER user.lastActiveAt >= @since
            SORT user.lastActiveAt DESC
            LIMIT @limit
            RETURN user
        """)
    List<SyntopiaUser> findRecentlyActiveUsers(
        @Param("since") LocalDateTime since,
        @Param("limit") Integer limit
    );
    
    // =================== TAO CONSCIOUSNESS ANALYTICS ===================
    
    /**
     * Count users by consciousness level (TAO Analytics)
     */
    @Query("""
        FOR user IN users
            COLLECT scl = user.currentSCL WITH COUNT INTO count
            SORT scl ASC
            RETURN {consciousnessLevel: scl, userCount: count}
        """)
    List<Object> getConsciousnessLevelDistribution();
    
    /**
     * Get consciousness evolution statistics (TAO Aggregation)
     */
    @Query("""
        FOR user IN users
            FILTER user.consciousnessScore != null
            COLLECT AGGREGATE 
                avgConsciousness = AVERAGE(user.consciousnessScore),
                maxConsciousness = MAX(user.consciousnessScore),
                minConsciousness = MIN(user.consciousnessScore),
                totalUsers = COUNT()
            RETURN {
                averageConsciousness: avgConsciousness,
                maximumConsciousness: maxConsciousness,
                minimumConsciousness: minConsciousness,
                totalUsers: totalUsers
            }
        """)
    Object getConsciousnessStatistics();
    
    /**
     * Find consciousness evolution leaders (TAO Ranking)
     */
    @Query("""
        FOR user IN users
            FILTER user.consciousnessScore != null
            SORT user.consciousnessScore DESC, user.currentSCL DESC
            LIMIT @limit
            RETURN {
                user: user,
                consciousnessRank: ROW_NUMBER()
            }
        """)
    List<Object> getConsciousnessLeaders(@Param("limit") Integer limit);
    
    // =================== TAO GENE KEYS QUERIES ===================
    
    /**
     * Find users with compatible Gene Keys (TAO Set Operations)
     */
    @Query("""
        FOR user IN users
            LET userGeneKeys = user.activatedGeneKeys
            LET targetGeneKeys = @targetGeneKeys
            LET commonKeys = INTERSECTION(userGeneKeys, targetGeneKeys)
            FILTER LENGTH(commonKeys) > 0
            SORT LENGTH(commonKeys) DESC
            LIMIT @limit
            RETURN {
                user: user,
                commonGeneKeys: commonKeys,
                compatibilityScore: LENGTH(commonKeys) / LENGTH(targetGeneKeys)
            }
        """)
    List<Object> findGeneKeysCompatibleUsers(
        @Param("targetGeneKeys") List<String> targetGeneKeys,
        @Param("limit") Integer limit
    );
    
    /**
     * Get Gene Keys distribution (TAO Analytics)
     */
    @Query("""
        FOR user IN users
            FOR geneKey IN user.activatedGeneKeys
                COLLECT key = geneKey WITH COUNT INTO usage
                SORT usage DESC
                LIMIT @limit
                RETURN {geneKey: key, usageCount: usage}
        """)
    List<Object> getGeneKeysDistribution(@Param("limit") Integer limit);
    
    // =================== TAO PERFORMANCE OPTIMIZATION ===================
    
    /**
     * Batch user lookup by IDs (TAO Bulk Operations)
     */
    @Query("""
        FOR userId IN @userIds
            FOR user IN users
                FILTER user._key == userId
                RETURN user
        """)
    List<SyntopiaUser> findUsersByIds(@Param("userIds") List<String> userIds);
    
    /**
     * Count total active users (TAO Performance Query)
     */
    @Query("""
        FOR user IN users
            FILTER user.lastActiveAt >= @activeThreshold
            COLLECT WITH COUNT INTO count
            RETURN count
        """)
    Long countActiveUsers(@Param("activeThreshold") LocalDateTime activeThreshold);
}
