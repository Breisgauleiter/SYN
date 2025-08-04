package org.syntopia.consciousness.platform.repository.tao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.ConsciousnessResonance;

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
public interface TAOConsciousnessResonanceRepository extends JpaRepository<ConsciousnessResonance, String> {

    // =================== JPA ASSOCIATION QUERIES ===================
    
    /**
     * Find high resonance connections for a user (simplified for H2)
     */
    @Query(value = "SELECT * FROM CONSCIOUSNESS_RESONANCE r WHERE r.SOURCE_USER_ID = :userId AND r.STRENGTH >= :minStrength AND r.IS_ACTIVE = true ORDER BY r.STRENGTH DESC LIMIT :limit", nativeQuery = true)
    List<ConsciousnessResonance> findHighResonanceConnections(
        @Param("userId") String userId,
        @Param("minStrength") Double minStrength,
        @Param("limit") Integer limit
    );
    
    /**
     * Find all active resonances for a user
     */
    @Query("SELECT r FROM ConsciousnessResonance r WHERE r.sourceUserId = :userId AND r.isActive = true ORDER BY r.lastCalculated DESC")
    List<ConsciousnessResonance> findActiveResonancesByUser(@Param("userId") String userId);
    
    // TODO: Re-implement with ArangoDB TAO queries when switching to production
}