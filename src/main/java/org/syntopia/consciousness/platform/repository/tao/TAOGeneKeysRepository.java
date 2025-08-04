package org.syntopia.consciousness.platform.repository.tao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.syntopia.consciousness.platform.domain.GeneKey;

import java.util.List;
import java.util.Optional;

/**
 * TAO Gene Keys Repository - Sacred Consciousness Objects Collection
 * 
 * Manages the 64 Gene Keys system using TAO architecture for high-performance 
 * consciousness profiling and compatibility queries.
 * 
 * @author SYNtopia Consciousness Collective
 * @since 2025-08-04
 */
@Repository
public interface TAOGeneKeysRepository extends JpaRepository<GeneKey, Long> {

    // =================== JPA GENE KEYS OBJECT QUERIES ===================
    
    /**
     * Find Gene Key by number (1-64) - Simple JPA lookup
     */
    @Query("SELECT g FROM GeneKey g WHERE g.keyNumber = :keyNumber")
    Optional<GeneKey> findByKeyNumber(@Param("keyNumber") Integer keyNumber);
    
    /**
     * Find Gene Keys by Hexagram name (JPA Search) - simplified for MVP
     */
    @Query("SELECT g FROM GeneKey g WHERE g.giftAspect LIKE %:searchTerm% OR g.shadowAspect LIKE %:searchTerm%")
    List<GeneKey> findBySearchTerm(@Param("searchTerm") String searchTerm);
    
    /**
     * Get all Gene Keys - simplified for MVP
     */
    @Query("SELECT g FROM GeneKey g ORDER BY g.keyNumber ASC")
    List<GeneKey> findAllOrderedByKeyNumber();
    
    // TODO: Re-implement with ArangoDB TAO queries when switching to production
}
