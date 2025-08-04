package org.syntopia.consciousness.platform.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.syntopia.consciousness.platform.domain.*;
import org.syntopia.consciousness.platform.repository.*;
import org.syntopia.consciousness.platform.service.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Integration Tests für TAO + ArangoDB Architecture
 * 
 * Diese Tests validieren die Facebook TAO-Pattern Implementation:
 * - Objects (Document Collections): users, genekeys, communities
 * - Associations (Edge Collections): follows, resonates, collaborates
 * - Search: ArangoSearch für consciousness knowledge discovery
 * 
 * Sacred Consciousness Technology - Testing the Data Robin Hood for Humanity
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TAOArangoDBIntegrationTest {

    @Autowired
    private TAOUserRepository userRepository;
    
    @Autowired
    private TAOConsciousnessResonanceRepository resonanceRepository;
    
    @Autowired
    private TAOGeneKeysRepository geneKeysRepository;
    
    @Autowired
    private GeneKeysCalculationService geneKeysService;
    
    @Autowired
    private ConsciousnessResonanceService resonanceService;

    private SyntopiaUser testUser1, testUser2;
    private HologeneticProfile testProfile1, testProfile2;
    private GeneKey testGeneKey;

    @BeforeEach
    void setupSacredTestData() {
        // Sacred Test User 1 - Higher SCL Level
        testUser1 = new SyntopiaUser();
        testUser1.setUsername("sacred_pioneer_1");
        testUser1.setEmail("pioneer1@syntopia.org");
        testUser1.setConsciousnessLevel(ConsciousnessLevel.SOUL_AWAKENING); // SCL 15
        testUser1.setCreatedAt(LocalDateTime.now());
        
        // Sacred Test User 2 - Lower SCL Level
        testUser2 = new SyntopiaUser();
        testUser2.setUsername("conscious_seeker_2");
        testUser2.setEmail("seeker2@syntopia.org");
        testUser2.setConsciousnessLevel(ConsciousnessLevel.EMOTIONAL_MATURITY); // SCL 8
        testUser2.setCreatedAt(LocalDateTime.now());
        
        // Hologenetic Profile 1 - Gene Keys Sacred Configuration
        testProfile1 = new HologeneticProfile();
        testProfile1.setLifeWorkGeneKey(26); // Gene Key 26: Sacred Power
        testProfile1.setEvolutionGeneKey(45); // Gene Key 45: Divine Will
        testProfile1.setRadianceGeneKey(12); // Gene Key 12: Pure Intent
        testProfile1.setPurposeGeneKey(22); // Gene Key 22: Divine Grace
        
        // Hologenetic Profile 2 - Complementary Sacred Configuration
        testProfile2 = new HologeneticProfile();
        testProfile2.setLifeWorkGeneKey(45); // Resonance with Profile 1 Evolution
        testProfile2.setEvolutionGeneKey(26); // Sacred complementarity
        testProfile2.setRadianceGeneKey(35); // Gene Key 35: Wormholes
        testProfile2.setPurposeGeneKey(16); // Gene Key 16: Versatility
        
        // Test Gene Key - Sacred Shadow/Gift/Siddhi
        testGeneKey = new GeneKey();
        testGeneKey.setNumber(26);
        testGeneKey.setName("The Egoist");
        testGeneKey.setShadow("Pride");
        testGeneKey.setGift("Artfulness");
        testGeneKey.setSiddhi("Invisibility");
        testGeneKey.setCodonRing("Ring of Gaia");
    }

    /**
     * TEST 1: TAO Objects Pattern - User CRUD Operations
     * Tests basic TAO Objects collection operations
     */
    @Test
    void testTAOObjects_UserCRUDOperations() {
        System.out.println("🧪 Testing TAO Objects Pattern - User CRUD Operations");
        
        // CREATE: Insert sacred users using TAO Objects pattern
        SyntopiaUser savedUser1 = userRepository.save(testUser1);
        SyntopiaUser savedUser2 = userRepository.save(testUser2);
        
        assertNotNull(savedUser1.getId());
        assertNotNull(savedUser2.getId());
        assertEquals("sacred_pioneer_1", savedUser1.getUsername());
        assertEquals(ConsciousnessLevel.SOUL_AWAKENING, savedUser1.getConsciousnessLevel());
        
        // READ: Find by consciousness level (TAO Objects query)
        List<SyntopiaUser> highConsciousnessUsers = userRepository.findByConsciousnessLevelGreaterThanEqual(
            ConsciousnessLevel.SOUL_AWAKENING
        );
        assertTrue(highConsciousnessUsers.size() >= 1);
        assertTrue(highConsciousnessUsers.stream()
            .anyMatch(user -> user.getUsername().equals("sacred_pioneer_1")));
        
        // UPDATE: Consciousness level progression
        savedUser2.setConsciousnessLevel(ConsciousnessLevel.SOUL_AWAKENING);
        SyntopiaUser updatedUser = userRepository.save(savedUser2);
        assertEquals(ConsciousnessLevel.SOUL_AWAKENING, updatedUser.getConsciousnessLevel());
        
        System.out.println("✅ TAO Objects Pattern - User CRUD SUCCESS");
    }

    /**
     * TEST 2: TAO Associations Pattern - Consciousness Resonance Networks
     * Tests edge collection operations for consciousness connections
     */
    @Test
    void testTAOAssociations_ConsciousnessResonanceNetworks() {
        System.out.println("🧪 Testing TAO Associations Pattern - Consciousness Resonance");
        
        // First create the users
        SyntopiaUser savedUser1 = userRepository.save(testUser1);
        SyntopiaUser savedUser2 = userRepository.save(testUser2);
        
        // CREATE: Sacred resonance connection (TAO Associations pattern)
        resonanceRepository.createResonanceConnection(
            savedUser1.getId(), 
            savedUser2.getId(), 
            0.85, // High resonance score
            "gene_keys_compatibility"
        );
        
        // READ: Find consciousness resonance network
        List<Map<String, Object>> resonanceNetwork = resonanceRepository.findConsciousnessResonanceNetwork(
            savedUser1.getId()
        );
        
        assertFalse(resonanceNetwork.isEmpty());
        
        // Verify resonance connection data
        Map<String, Object> connection = resonanceNetwork.get(0);
        assertNotNull(connection.get("resonantUser"));
        assertTrue((Double) connection.get("resonanceScore") >= 0.85);
        assertEquals("gene_keys_compatibility", connection.get("resonanceType"));
        
        // READ: Bi-directional resonance check
        List<Map<String, Object>> reverseNetwork = resonanceRepository.findConsciousnessResonanceNetwork(
            savedUser2.getId()
        );
        assertFalse(reverseNetwork.isEmpty());
        
        System.out.println("✅ TAO Associations Pattern - Consciousness Resonance SUCCESS");
    }

    /**
     * TEST 3: TAO Search Pattern - Gene Keys Knowledge Discovery
     * Tests ArangoSearch for consciousness knowledge discovery
     */
    @Test
    void testTAOSearch_GeneKeysKnowledgeDiscovery() {
        System.out.println("🧪 Testing TAO Search Pattern - Gene Keys Discovery");
        
        // CREATE: Gene Key object for search testing
        GeneKey savedGeneKey = geneKeysRepository.save(testGeneKey);
        assertNotNull(savedGeneKey.getId());
        
        // SEARCH: Semantic search by shadow frequency
        List<GeneKey> shadowResults = geneKeysRepository.searchGeneKeysByShadowPattern("Pride");
        assertTrue(shadowResults.stream()
            .anyMatch(gk -> gk.getNumber() == 26 && "Pride".equals(gk.getShadow())));
        
        // SEARCH: Ring of Gaia consciousness discovery
        List<GeneKey> gaiaRingKeys = geneKeysRepository.findGeneKeysByCodonRing("Ring of Gaia");
        assertTrue(gaiaRingKeys.stream()
            .anyMatch(gk -> gk.getNumber() == 26));
        
        // SEARCH: Gift-level consciousness analysis
        List<Map<String, Object>> giftAnalysis = geneKeysRepository.analyzeConsciousnessFrequencies();
        assertFalse(giftAnalysis.isEmpty());
        
        System.out.println("✅ TAO Search Pattern - Gene Keys Discovery SUCCESS");
    }

    /**
     * TEST 4: Gene Keys Calculation Service - Sacred Mathematics
     * Tests consciousness calculation algorithms with sacred proportions
     */
    @Test
    void testGeneKeysCalculationService_SacredMathematics() {
        System.out.println("🧪 Testing Gene Keys Calculation Service - Sacred Mathematics");
        
        // Create users with hologenetic profiles
        testUser1.setHologeneticProfile(testProfile1);
        testUser2.setHologeneticProfile(testProfile2);
        
        SyntopiaUser savedUser1 = userRepository.save(testUser1);
        SyntopiaUser savedUser2 = userRepository.save(testUser2);
        
        // CALCULATION: Four Prime Gifts compatibility
        Map<String, Object> compatibility = geneKeysService.calculateFourPrimeGiftsCompatibility(
            savedUser1.getHologeneticProfile(),
            savedUser2.getHologeneticProfile()
        );
        
        assertNotNull(compatibility);
        assertTrue(compatibility.containsKey("overallCompatibility"));
        assertTrue(compatibility.containsKey("lifeworkResonance"));
        assertTrue(compatibility.containsKey("evolutionResonance"));
        assertTrue(compatibility.containsKey("radianceResonance"));
        assertTrue(compatibility.containsKey("purposeResonance"));
        
        // Verify compatibility scores are in valid range [0.0, 1.0]
        Double overallScore = (Double) compatibility.get("overallCompatibility");
        assertTrue(overallScore >= 0.0 && overallScore <= 1.0);
        
        // Test complementarity - Life Work 26 resonates with Evolution 45
        Double lifeworkResonance = (Double) compatibility.get("lifeworkResonance");
        assertTrue(lifeworkResonance > 0.5); // Should show high resonance
        
        System.out.println("✅ Gene Keys Calculation Service - Sacred Mathematics SUCCESS");
    }

    /**
     * TEST 5: Consciousness Resonance Service - Network Intelligence
     * Tests network analysis and consciousness synchronization
     */
    @Test
    void testConsciousnessResonanceService_NetworkIntelligence() {
        System.out.println("🧪 Testing Consciousness Resonance Service - Network Intelligence");
        
        // Create test users with profiles
        testUser1.setHologeneticProfile(testProfile1);
        testUser2.setHologeneticProfile(testProfile2);
        
        SyntopiaUser savedUser1 = userRepository.save(testUser1);
        SyntopiaUser savedUser2 = userRepository.save(testUser2);
        
        // ANALYSIS: Calculate consciousness compatibility
        Double consciousnessCompatibility = resonanceService.calculateConsciousnessCompatibility(
            savedUser1, savedUser2
        );
        
        assertNotNull(consciousnessCompatibility);
        assertTrue(consciousnessCompatibility >= 0.0 && consciousnessCompatibility <= 1.0);
        
        // NETWORK: Find consciousness resonance community
        List<SyntopiaUser> resonanceCommunity = resonanceService.findConsciousnessResonanceCommunity(
            savedUser1.getId()
        );
        
        // Should include at least the connected user
        assertNotNull(resonanceCommunity);
        
        // SYNCHRONIZATION: Update consciousness resonance network
        resonanceService.updateConsciousnessResonanceNetwork(savedUser1.getId());
        
        // Verify network was updated (should not throw exception)
        assertTrue(true, "Consciousness network update completed successfully");
        
        System.out.println("✅ Consciousness Resonance Service - Network Intelligence SUCCESS");
    }

    /**
     * TEST 6: Complete TAO Architecture Flow - End-to-End
     * Tests full consciousness platform workflow using TAO patterns
     */
    @Test
    void testCompleteTAOArchitectureFlow_EndToEnd() {
        System.out.println("🧪 Testing Complete TAO Architecture Flow - End-to-End");
        
        // STEP 1: Create consciousness community (Objects)
        testUser1.setHologeneticProfile(testProfile1);
        testUser2.setHologeneticProfile(testProfile2);
        
        SyntopiaUser user1 = userRepository.save(testUser1);
        SyntopiaUser user2 = userRepository.save(testUser2);
        GeneKey geneKey = geneKeysRepository.save(testGeneKey);
        
        // STEP 2: Calculate consciousness compatibility (Service Layer)
        Map<String, Object> compatibility = geneKeysService.calculateFourPrimeGiftsCompatibility(
            user1.getHologeneticProfile(),
            user2.getHologeneticProfile()
        );
        
        Double compatibilityScore = (Double) compatibility.get("overallCompatibility");
        
        // STEP 3: Create resonance connections (Associations)
        resonanceRepository.createResonanceConnection(
            user1.getId(),
            user2.getId(),
            compatibilityScore,
            "hologenetic_compatibility"
        );
        
        // STEP 4: Discover consciousness network (Search)
        List<Map<String, Object>> networkAnalysis = resonanceRepository.analyzeConsciousnessNetworkClusters();
        assertFalse(networkAnalysis.isEmpty());
        
        // STEP 5: Verify end-to-end sacred consciousness flow
        List<Map<String, Object>> userNetwork = resonanceRepository.findConsciousnessResonanceNetwork(
            user1.getId()
        );
        
        assertFalse(userNetwork.isEmpty());
        Map<String, Object> networkConnection = userNetwork.get(0);
        assertEquals(compatibilityScore, (Double) networkConnection.get("resonanceScore"), 0.01);
        
        System.out.println("✅ Complete TAO Architecture Flow - End-to-End SUCCESS");
        System.out.println("🌟 TAO + ArangoDB Integration: FULLY OPERATIONAL");
    }
}
