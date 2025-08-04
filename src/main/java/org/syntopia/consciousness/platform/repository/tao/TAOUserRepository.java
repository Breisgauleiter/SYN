package org.syntopia.consciousness.platform.repository.tao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TAO User Repository - JPA Implementation for H2 Development
 * 
 * Simplified JPA version for development. Will be replaced with
 * ArangoDB TAO implementation in production.
 * 
 * @author SYNtopia Consciousness Collective
 * @since 2025-08-04
 */
@Repository
public interface TAOUserRepository extends JpaRepository<SyntopiaUser, String> {

    // =================== BASIC JPA QUERIES ===================
    
    /**
     * Find user by username
     */
    Optional<SyntopiaUser> findByUsername(String username);
    
    /**
     * Find user by email
     */
    Optional<SyntopiaUser> findByEmail(String email);
    
    /**
     * Find users by consciousness level range - JPA JPQL version
     */
    @Query(value = "SELECT * FROM SYNTOPIA_USER u WHERE u.CURRENT_LEVEL >= :minSCL AND u.CURRENT_LEVEL <= :maxSCL ORDER BY u.CURRENT_LEVEL DESC LIMIT :limit", nativeQuery = true)
    List<SyntopiaUser> findByConsciousnessLevelRange(
        @Param("minSCL") Integer minSCL, 
        @Param("maxSCL") Integer maxSCL,
        @Param("limit") Integer limit
    );
    
    /**
     * Find users by Business Track - JPA version
     */
    @Query("SELECT u FROM SyntopiaUser u WHERE u.primaryBusinessTrack = :track")
    List<SyntopiaUser> findByBusinessTrack(@Param("track") String track);
    
    /**
     * Find active users in last N days
     */
    @Query("SELECT u FROM SyntopiaUser u WHERE u.lastActiveAt >= :since ORDER BY u.lastActiveAt DESC")
    List<SyntopiaUser> findActiveUsersSince(@Param("since") LocalDateTime since);
    
    /**
     * Find users who completed hologenetic profile
     */
    @Query("SELECT u FROM SyntopiaUser u WHERE u.profileCompletedAt IS NOT NULL ORDER BY u.profileCompletedAt DESC")
    List<SyntopiaUser> findUsersWithCompletedProfiles();

    // TODO: Re-implement with ArangoDB TAO queries when switching to production
}
