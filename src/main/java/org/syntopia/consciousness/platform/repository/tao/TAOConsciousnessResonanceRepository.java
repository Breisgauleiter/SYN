package org.syntopia.consciousness.platform.repository.tao;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.ConsciousnessResonance;
import org.syntopia.consciousness.platform.domain.ResonanceFactors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TAO Consciousness Resonance Repository - Associations Collection
 * 
 * Manages consciousness connections between users using Facebook's TAO pattern.
 * Handles high-performance relationship queries for the consciousness network.
 * 
 * @author SYNtopia Consciousness Collective  
 * @since 2025-08-04
 */
@Repository
public interface TAOConsciousnessResonanceRepository extends ArangoRepository<ConsciousnessResonance, String> {

    // =================== TAO ASSOCIATION QUERIES ===================
    
    /**
     * Find high resonance connections for a user (TAO Association traversal)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance._from == CONCAT('users/', @userId)
            FILTER resonance.strength >= @minStrength
            FILTER resonance.isActive == true
            SORT resonance.strength DESC, resonance.lastCalculated DESC
            LIMIT @limit
            RETURN resonance
        """)
    List<ConsciousnessResonance> findHighResonanceConnections(
        @Param("userId") String userId,
        @Param("minStrength") Double minStrength,
        @Param("limit") Integer limit
    );
    
    /**
     * Find mutual consciousness connections (TAO Bidirectional Association)
     */
    @Query("""
        FOR user1 IN users
            FILTER user1._key == @userId1
            FOR user2 IN users  
                FILTER user2._key == @userId2
                LET connection1 = (
                    FOR resonance IN consciousness_resonance
                        FILTER resonance._from == user1._id
                        FILTER resonance._to == user2._id
                        FILTER resonance.isActive == true
                        RETURN resonance
                )
                LET connection2 = (
                    FOR resonance IN consciousness_resonance
                        FILTER resonance._from == user2._id
                        FILTER resonance._to == user1._id
                        FILTER resonance.isActive == true
                        RETURN resonance
                )
                FILTER LENGTH(connection1) > 0 AND LENGTH(connection2) > 0
                RETURN {
                    user1ToUser2: FIRST(connection1),
                    user2ToUser1: FIRST(connection2),
                    mutualStrength: (FIRST(connection1).strength + FIRST(connection2).strength) / 2
                }
        """)
    List<Object> findMutualResonance(@Param("userId1") String userId1, @Param("userId2") String userId2);
    
    /**
     * Find users with strongest consciousness resonance in network (TAO Graph Analytics)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance.strength >= @minStrength
            FILTER resonance.lastCalculated >= @since
            COLLECT fromUser = resonance._from WITH COUNT INTO outgoingCount
            COLLECT toUser = resonance._to WITH COUNT INTO incomingCount
            LET userId = SPLIT(fromUser, '/')[1] || SPLIT(toUser, '/')[1]
            COLLECT user = userId 
                AGGREGATE totalConnections = SUM(outgoingCount + incomingCount)
            SORT totalConnections DESC
            LIMIT @limit
            RETURN {
                userId: user,
                totalResonanceConnections: totalConnections
            }
        """)
    List<Object> findMostConnectedUsers(
        @Param("minStrength") Double minStrength,
        @Param("since") LocalDateTime since,
        @Param("limit") Integer limit
    );
    
    /**
     * Find consciousness resonance by Gene Keys compatibility (TAO Complex Association)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance.resonanceFactors.geneKeysCompatibility >= @minCompatibility
            FILTER resonance.isActive == true
            FOR fromUser IN users
                FILTER fromUser._id == resonance._from
                FOR toUser IN users
                    FILTER toUser._id == resonance._to
                    SORT resonance.resonanceFactors.geneKeysCompatibility DESC
                    LIMIT @limit
                    RETURN {
                        resonance: resonance,
                        fromUser: {username: fromUser.username, geneKeys: fromUser.activatedGeneKeys},
                        toUser: {username: toUser.username, geneKeys: toUser.activatedGeneKeys},
                        geneKeysCompatibility: resonance.resonanceFactors.geneKeysCompatibility
                    }
        """)
    List<Object> findGeneKeysResonance(
        @Param("minCompatibility") Double minCompatibility,
        @Param("limit") Integer limit
    );
    
    // =================== TAO CONSCIOUSNESS NETWORK ANALYTICS ===================
    
    /**
     * Calculate network consciousness density (TAO Network Metrics)
     */
    @Query("""
        LET totalUsers = LENGTH(FOR user IN users RETURN 1)
        LET totalConnections = LENGTH(
            FOR resonance IN consciousness_resonance
                FILTER resonance.isActive == true
                RETURN 1
        )
        LET maxPossibleConnections = totalUsers * (totalUsers - 1)
        RETURN {
            totalUsers: totalUsers,
            activeConnections: totalConnections,
            networkDensity: totalConnections / maxPossibleConnections,
            averageConnectionsPerUser: totalConnections / totalUsers
        }
        """)
    Object calculateNetworkDensity();
    
    /**
     * Find consciousness evolution patterns (TAO Temporal Analytics)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance.lastCalculated >= @since
            COLLECT month = DATE_TRUNC(resonance.lastCalculated, 'month') 
                AGGREGATE avgStrength = AVERAGE(resonance.strength),
                         connectionCount = COUNT()
            SORT month ASC
            RETURN {
                month: month,
                averageResonanceStrength: avgStrength,
                newConnections: connectionCount
            }
        """)
    List<Object> getConsciousnessEvolutionTrends(@Param("since") LocalDateTime since);
    
    /**
     * Find consciousness communities via resonance clustering (TAO Community Detection)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance.strength >= @minStrength
            FILTER resonance.isActive == true
            FOR fromUser IN users
                FILTER fromUser._id == resonance._from
                FOR toUser IN users
                    FILTER toUser._id == resonance._to
                    COLLECT community = fromUser.activatedGeneKeys[0] 
                        WITH COUNT INTO memberCount
                        AGGREGATE avgResonance = AVERAGE(resonance.strength)
                    FILTER memberCount >= @minCommunitySize
                    SORT avgResonance DESC
                    LIMIT @limit
                    RETURN {
                        communityGeneKey: community,
                        memberCount: memberCount,
                        averageResonance: avgResonance
                    }
        """)
    List<Object> findConsciousnessCommunities(
        @Param("minStrength") Double minStrength,
        @Param("minCommunitySize") Integer minCommunitySize,
        @Param("limit") Integer limit
    );
    
    // =================== TAO PERFORMANCE QUERIES ===================
    
    /**
     * Get user's total resonance score (TAO Aggregation)
     */
    @Query("""
        FOR resonance IN consciousness_resonance
            FILTER resonance._from == CONCAT('users/', @userId)
            FILTER resonance.isActive == true
            COLLECT AGGREGATE totalResonance = SUM(resonance.strength),
                             connectionCount = COUNT()
            RETURN {
                userId: @userId,
                totalResonanceScore: totalResonance,
                activeConnections: connectionCount,
                averageResonance: totalResonance / connectionCount
            }
        """)
    Object getUserResonanceScore(@Param("userId") String userId);
    
    /**
     * Batch resonance lookup for multiple users (TAO Bulk Operations)
     */
    @Query("""
        FOR userId IN @userIds
            FOR resonance IN consciousness_resonance
                FILTER resonance._from == CONCAT('users/', userId)
                FILTER resonance.isActive == true
                COLLECT user = userId 
                    AGGREGATE totalStrength = SUM(resonance.strength),
                             connectionCount = COUNT()
                RETURN {
                    userId: user,
                    totalResonance: totalStrength,
                    connections: connectionCount
                }
        """)
    List<Object> getBatchResonanceScores(@Param("userIds") List<String> userIds);
}