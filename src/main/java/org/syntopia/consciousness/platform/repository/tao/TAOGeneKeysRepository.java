package org.syntopia.consciousness.platform.repository.tao;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
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
public interface TAOGeneKeysRepository extends ArangoRepository<GeneKey, String> {

    // =================== TAO GENE KEYS OBJECT QUERIES ===================
    
    /**
     * Find Gene Key by number (1-64) - TAO Object lookup
     */
    @Query("FOR genekey IN genekeys FILTER genekey.keyNumber == @keyNumber RETURN genekey")
    Optional<GeneKey> findByKeyNumber(@Param("keyNumber") Integer keyNumber);
    
    /**
     * Find Gene Keys by Codon Ring (TAO Object filtering)
     */
    @Query("""
        FOR genekey IN genekeys
            FILTER genekey.codonRing == @codonRing
            SORT genekey.keyNumber ASC
            RETURN genekey
        """)
    List<GeneKey> findByCodonRing(@Param("codonRing") Integer codonRing);
    
    /**
     * Find Gene Keys by I-Ching Hexagram name (TAO Search)
     */
    @Query("""
        FOR genekey IN genekeys
            FILTER genekey.hexagramName == @hexagramName
            RETURN genekey
        """)
    Optional<GeneKey> findByHexagramName(@Param("hexagramName") String hexagramName);
    
    /**
     * Find Gene Keys compatible with business track (TAO Complex filtering)
     */
    @Query("""
        FOR genekey IN genekeys
            FILTER genekey.businessApplications[@businessTrack] == "high" OR 
                   genekey.businessApplications[@businessTrack] == "very_high"
            SORT genekey.consciousnessPower DESC
            LIMIT @limit
            RETURN genekey
        """)
    List<GeneKey> findCompatibleWithBusinessTrack(
        @Param("businessTrack") String businessTrack,
        @Param("limit") Integer limit
    );
    
    // =================== TAO CONSCIOUSNESS LEVEL QUERIES ===================
    
    /**
     * Find Gene Keys by consciousness frequency (Shadow/Gift/Siddhi) - TAO Analytics
     */
    @Query("""
        FOR genekey IN genekeys
            FILTER genekey.consciousness[@frequency] != null
            SORT genekey.consciousnessPower DESC
            LIMIT @limit
            RETURN {
                geneKey: genekey,
                frequency: @frequency,
                description: genekey.consciousness[@frequency]
            }
        """)
    List<Object> findByConsciousnessFrequency(
        @Param("frequency") String frequency, // "shadow", "gift", "siddhi"
        @Param("limit") Integer limit
    );
    
    /**
     * Get Gene Keys evolution pathway (Shadow → Gift → Siddhi) - TAO Transformation
     */
    @Query("""
        FOR genekey IN genekeys
            FILTER genekey.keyNumber == @keyNumber
            RETURN {
                geneKey: genekey.keyNumber,
                hexagram: genekey.hexagramName,
                evolutionPath: {
                    shadow: genekey.consciousness.shadow,
                    gift: genekey.consciousness.gift,
                    siddhi: genekey.consciousness.siddhi
                },
                businessApplications: genekey.businessApplications,
                codonRing: genekey.codonRing
            }
        """)
    Optional<Object> getEvolutionPathway(@Param("keyNumber") Integer keyNumber);
    
    // =================== TAO CODON RING ANALYTICS ===================
    
    /**
     * Get all 22 Codon Rings with their Gene Keys (TAO Hierarchical query)
     */
    @Query("""
        FOR genekey IN genekeys
            COLLECT ring = genekey.codonRing INTO geneKeysInRing
            SORT ring ASC
            RETURN {
                codonRing: ring,
                geneKeys: geneKeysInRing[*].genekey.keyNumber,
                ringTheme: FIRST(geneKeysInRing).genekey.ringTheme,
                totalKeys: LENGTH(geneKeysInRing)
            }
        """)
    List<Object> getAllCodonRings();
    
    /**
     * Find complementary Gene Keys for consciousness balancing (TAO Compatibility)
     */
    @Query("""
        FOR baseKey IN genekeys
            FILTER baseKey.keyNumber == @baseKeyNumber
            FOR complementKey IN genekeys
                FILTER complementKey.codonRing != baseKey.codonRing
                LET shadowBalance = (
                    baseKey.consciousness.shadow != complementKey.consciousness.gift AND
                    baseKey.consciousness.gift != complementKey.consciousness.shadow
                )
                FILTER shadowBalance == true
                SORT complementKey.consciousnessPower DESC
                LIMIT @limit
                RETURN {
                    baseGeneKey: baseKey,
                    complementaryKey: complementKey,
                    balanceReason: "Shadow-Gift complementarity",
                    synergy: complementKey.consciousnessPower + baseKey.consciousnessPower
                }
        """)
    List<Object> findComplementaryGeneKeys(
        @Param("baseKeyNumber") Integer baseKeyNumber,
        @Param("limit") Integer limit
    );
    
    // =================== TAO BUSINESS INTEGRATION QUERIES ===================
    
    /**
     * Find Gene Keys for specific business roles (TAO Business Intelligence)
     */
    @Query("""
        FOR genekey IN genekeys
            LET businessScore = (
                TONUMBER(genekey.businessApplications.leadership == "high") * 3 +
                TONUMBER(genekey.businessApplications.creativity == "high") * 2 +
                TONUMBER(genekey.businessApplications.innovation == "high") * 2 +
                TONUMBER(genekey.businessApplications.collaboration == "high") * 1
            )
            FILTER businessScore >= @minBusinessScore
            SORT businessScore DESC, genekey.consciousnessPower DESC
            LIMIT @limit
            RETURN {
                geneKey: genekey,
                businessScore: businessScore,
                strongestApplications: genekey.businessApplications
            }
        """)
    List<Object> findBusinessOptimizedGeneKeys(
        @Param("minBusinessScore") Integer minBusinessScore,
        @Param("limit") Integer limit
    );
    
    /**
     * Calculate Gene Key compatibility matrix (TAO Network Analysis)
     */
    @Query("""
        FOR key1 IN genekeys
            FOR key2 IN genekeys
                FILTER key1.keyNumber < key2.keyNumber
                LET codonRingCompatibility = (key1.codonRing == key2.codonRing ? 0.8 : 0.3)
                LET consciousnessCompatibility = (
                    TONUMBER(key1.consciousness.gift == key2.consciousness.gift) * 0.9 +
                    TONUMBER(key1.consciousness.siddhi == key2.consciousness.siddhi) * 1.0 +
                    TONUMBER(key1.consciousness.shadow != key2.consciousness.shadow) * 0.6
                )
                LET businessCompatibility = (
                    LENGTH(INTERSECTION(VALUES(key1.businessApplications), VALUES(key2.businessApplications))) / 
                    LENGTH(UNION(VALUES(key1.businessApplications), VALUES(key2.businessApplications)))
                )
                LET totalCompatibility = (
                    codonRingCompatibility + consciousnessCompatibility + businessCompatibility
                ) / 3
                FILTER totalCompatibility >= @minCompatibility
                SORT totalCompatibility DESC
                LIMIT @limit
                RETURN {
                    geneKey1: key1.keyNumber,
                    geneKey2: key2.keyNumber,
                    compatibility: totalCompatibility,
                    factors: {
                        codonRing: codonRingCompatibility,
                        consciousness: consciousnessCompatibility,
                        business: businessCompatibility
                    }
                }
        """)
    List<Object> calculateCompatibilityMatrix(
        @Param("minCompatibility") Double minCompatibility,
        @Param("limit") Integer limit
    );
    
    // =================== TAO CONSCIOUSNESS STATISTICS ===================
    
    /**
     * Get Gene Keys consciousness power distribution (TAO Analytics)
     */
    @Query("""
        FOR genekey IN genekeys
            COLLECT powerLevel = FLOOR(genekey.consciousnessPower) WITH COUNT INTO count
            SORT powerLevel ASC
            RETURN {
                consciousnessPowerLevel: powerLevel,
                geneKeysCount: count
            }
        """)
    List<Object> getConsciousnessPowerDistribution();
    
    /**
     * Find most powerful Gene Keys for consciousness evolution (TAO Ranking)
     */
    @Query("""
        FOR genekey IN genekeys
            SORT genekey.consciousnessPower DESC
            LIMIT @limit
            RETURN {
                rank: ROW_NUMBER(),
                geneKey: genekey.keyNumber,
                hexagram: genekey.hexagramName,
                consciousnessPower: genekey.consciousnessPower,
                primaryGift: genekey.consciousness.gift,
                ultimateSiddhi: genekey.consciousness.siddhi
            }
        """)
    List<Object> getMostPowerfulGeneKeys(@Param("limit") Integer limit);
    
    /**
     * Get Codon Ring themes and consciousness focus areas (TAO Wisdom)
     */
    @Query("""
        FOR genekey IN genekeys
            COLLECT ring = genekey.codonRing 
                AGGREGATE avgPower = AVERAGE(genekey.consciousnessPower),
                         keyCount = COUNT(),
                         themes = UNIQUE(genekey.ringTheme)
            SORT ring ASC
            RETURN {
                codonRing: ring,
                theme: FIRST(themes),
                averageConsciousnessPower: avgPower,
                geneKeysCount: keyCount,
                focusArea: (
                    avgPower > 8 ? "High Consciousness" :
                    avgPower > 6 ? "Balanced Consciousness" :
                    "Foundation Consciousness"
                )
            }
        """)
    List<Object> getCodonRingThemes();
    
    // =================== TAO PERFORMANCE OPTIMIZATION ===================
    
    /**
     * Batch lookup Gene Keys by numbers (TAO Bulk Operations)
     */
    @Query("""
        FOR keyNumber IN @keyNumbers
            FOR genekey IN genekeys
                FILTER genekey.keyNumber == keyNumber
                RETURN genekey
        """)
    List<GeneKey> findGeneKeysByNumbers(@Param("keyNumbers") List<Integer> keyNumbers);
    
    /**
     * Count Gene Keys by business applications (TAO Aggregation)
     */
    @Query("""
        FOR genekey IN genekeys
            FOR appType IN ATTRIBUTES(genekey.businessApplications)
                FILTER genekey.businessApplications[appType] IN ["high", "very_high"]
                COLLECT application = appType WITH COUNT INTO usage
                SORT usage DESC
                RETURN {
                    businessApplication: application,
                    highCompatibilityCount: usage
                }
        """)
    List<Object> getBusinessApplicationsDistribution();
}
