package org.syntopia.consciousness.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.syntopia.consciousness.platform.domain.*;
import org.syntopia.consciousness.platform.repository.tao.TAOUserRepository;
import org.syntopia.consciousness.platform.repository.tao.TAOConsciousnessResonanceRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Consciousness Resonance Service - Sacred Network Analysis Engine
 * 
 * Calculates consciousness compatibility and resonance between users using
 * TAO + ArangoDB architecture for high-performance network analysis.
 * 
 * Implements ethical AI with privacy-first consciousness matching.
 * 
 * @author SYNtopia Consciousness Collective
 * @since 2025-08-04
 */
@Service
public class ConsciousnessResonanceService {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsciousnessResonanceService.class);
    
    @Autowired
    private TAOUserRepository userRepository;
    
    @Autowired
    private TAOConsciousnessResonanceRepository resonanceRepository;
    
    // Sacred thresholds for consciousness resonance
    private static final double HIGH_RESONANCE_THRESHOLD = 0.8;
    private static final double MEDIUM_RESONANCE_THRESHOLD = 0.6;
    private static final double GOLDEN_RATIO = 1.618033988749895;
    
    /**
     * Calculate consciousness resonance between two users
     * 
     * @param user1Id First user for resonance calculation
     * @param user2Id Second user for resonance calculation
     * @return Resonance score and detailed factors
     */
    public ResonanceResult calculateResonance(String user1Id, String user2Id) {
        logger.info("🌟 Calculating consciousness resonance between {} and {}", user1Id, user2Id);
        
        try {
            Optional<SyntopiaUser> user1Opt = userRepository.findById(user1Id);
            Optional<SyntopiaUser> user2Opt = userRepository.findById(user2Id);
            
            if (user1Opt.isEmpty() || user2Opt.isEmpty()) {
                throw new ResonanceCalculationException("One or both users not found");
            }
            
            SyntopiaUser user1 = user1Opt.get();
            SyntopiaUser user2 = user2Opt.get();
            
            // Calculate different resonance factors
            double geneKeysCompatibility = calculateGeneKeysCompatibility(user1, user2);
            double consciousnessLevelSynergy = calculateConsciousnessLevelSynergy(user1, user2);
            double businessTrackAlignment = calculateBusinessTrackAlignment(user1, user2);
            double synchronicityFactor = calculateSynchronicityFactor(user1, user2);
            
            // Weighted total resonance using sacred mathematics
            double totalResonance = calculateWeightedResonance(
                geneKeysCompatibility,
                consciousnessLevelSynergy, 
                businessTrackAlignment,
                synchronicityFactor
            );
            
            ResonanceResult result = ResonanceResult.builder()
                    .user1Id(user1Id)
                    .user2Id(user2Id)
                    .totalResonance(totalResonance)
                    .geneKeysCompatibility(geneKeysCompatibility)
                    .consciousnessLevelSynergy(consciousnessLevelSynergy)
                    .businessTrackAlignment(businessTrackAlignment)
                    .synchronicityFactor(synchronicityFactor)
                    .resonanceLevel(determineResonanceLevel(totalResonance))
                    .calculatedAt(LocalDateTime.now())
                    .build();
            
            logger.info("✨ Resonance calculated: {} (Level: {})", 
                       String.format("%.3f", totalResonance), result.resonanceLevel);
            
            return result;
            
        } catch (Exception e) {
            logger.error("❌ Error calculating resonance: {}", e.getMessage(), e);
            throw new ResonanceCalculationException("Failed to calculate consciousness resonance", e);
        }
    }
    
    /**
     * Find high resonance connections for a user
     */
    @Cacheable(value = "highResonanceConnections", key = "#userId")
    public List<ResonanceConnection> findHighResonanceConnections(String userId, int limit) {
        logger.info("🔍 Finding high resonance connections for user: {}", userId);
        
        try {
            List<ConsciousnessResonance> connections = resonanceRepository.findHighResonanceConnections(
                userId, HIGH_RESONANCE_THRESHOLD, limit
            );
            
            return connections.stream()
                    .map(this::mapToResonanceConnection)
                    .sorted((a, b) -> Double.compare(b.getResonanceStrength(), a.getResonanceStrength()))
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            logger.error("❌ Error finding high resonance connections: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Calculate Gene Keys compatibility between users
     */
    private double calculateGeneKeysCompatibility(SyntopiaUser user1, SyntopiaUser user2) {
        List<Integer> geneKeys1 = user1.getActivatedGeneKeys();
        List<Integer> geneKeys2 = user2.getActivatedGeneKeys();
        
        if (geneKeys1.isEmpty() || geneKeys2.isEmpty()) {
            return 0.0;
        }
        
        // Convert to Sets for intersection/union calculations
        Set<Integer> geneKeySet1 = new HashSet<>(geneKeys1);
        Set<Integer> geneKeySet2 = new HashSet<>(geneKeys2);
        
        // Calculate intersection and union
        Set<Integer> intersection = new HashSet<>(geneKeySet1);
        intersection.retainAll(geneKeySet2);
        
        Set<Integer> union = new HashSet<>(geneKeySet1);
        union.addAll(geneKeySet2);
        
        // Jaccard similarity with Golden Ratio enhancement
        double jaccard = (double) intersection.size() / union.size();
        
        // Enhance with Codon Ring compatibility
        double codonRingBonus = calculateCodonRingCompatibility(geneKeySet1, geneKeySet2);
        
        // Apply Golden Ratio for sacred mathematics
        return Math.min(1.0, (jaccard + codonRingBonus) * GOLDEN_RATIO / 2.0);
    }
    
    /**
     * Calculate Codon Ring compatibility bonus
     */
    private double calculateCodonRingCompatibility(Set<Integer> geneKeys1, Set<Integer> geneKeys2) {
        // Convert Integer sets to String sets for ring calculation
        Set<String> stringKeys1 = geneKeys1.stream().map(String::valueOf).collect(Collectors.toSet());
        Set<String> stringKeys2 = geneKeys2.stream().map(String::valueOf).collect(Collectors.toSet());
        
        // This would query the actual Codon Ring relationships
        // For now, simplified calculation
        Map<String, Integer> ringCounts1 = calculateRingDistribution(stringKeys1);
        Map<String, Integer> ringCounts2 = calculateRingDistribution(stringKeys2);
        
        double compatibility = 0.0;
        for (String ring : ringCounts1.keySet()) {
            if (ringCounts2.containsKey(ring)) {
                // Bonus for shared rings
                compatibility += 0.1;
            }
        }
        
        return Math.min(0.3, compatibility); // Max 30% bonus
    }
    
    /**
     * Calculate consciousness level synergy
     */
    private double calculateConsciousnessLevelSynergy(SyntopiaUser user1, SyntopiaUser user2) {
        Integer scl1 = user1.getCurrentSCL();
        Integer scl2 = user2.getCurrentSCL();
        
        if (scl1 == null || scl2 == null) {
            return 0.5; // Neutral if unknown
        }
        
        // Sweet spot is similar levels or complementary growth
        int levelDifference = Math.abs(scl1 - scl2);
        
        if (levelDifference == 0) {
            return 1.0; // Perfect match
        } else if (levelDifference <= 2) {
            return 0.9; // Very compatible
        } else if (levelDifference <= 5) {
            return 0.7; // Good compatibility
        } else {
            // Large differences can still work with mentor/student dynamic
            return Math.max(0.3, 1.0 - (levelDifference * 0.05));
        }
    }
    
    /**
     * Calculate business track alignment
     */
    private double calculateBusinessTrackAlignment(SyntopiaUser user1, SyntopiaUser user2) {
        Set<String> tracks1 = extractBusinessTracks(user1);
        Set<String> tracks2 = extractBusinessTracks(user2);
        
        if (tracks1.isEmpty() || tracks2.isEmpty()) {
            return 0.5; // Neutral if unknown
        }
        
        // Calculate overlap
        Set<String> intersection = new HashSet<>(tracks1);
        intersection.retainAll(tracks2);
        
        Set<String> union = new HashSet<>(tracks1);
        union.addAll(tracks2);
        
        double directAlignment = (double) intersection.size() / Math.max(tracks1.size(), tracks2.size());
        
        // Bonus for complementary tracks (e.g., Tech + Design)
        double complementaryBonus = calculateComplementaryTrackBonus(tracks1, tracks2);
        
        return Math.min(1.0, directAlignment + complementaryBonus);
    }
    
    /**
     * Calculate synchronicity factor using sacred timing
     */
    private double calculateSynchronicityFactor(SyntopiaUser user1, SyntopiaUser user2) {
        LocalDateTime lastActive1 = user1.getLastActiveAt();
        LocalDateTime lastActive2 = user2.getLastActiveAt();
        
        if (lastActive1 == null || lastActive2 == null) {
            return 0.5;
        }
        
        // Calculate temporal synchronicity
        long hoursDifference = Math.abs(
            java.time.Duration.between(lastActive1, lastActive2).toHours()
        );
        
        // Sacred timing patterns (Fibonacci hours)
        int[] fibonacciHours = {1, 1, 2, 3, 5, 8, 13, 21};
        
        for (int fibHour : fibonacciHours) {
            if (hoursDifference <= fibHour) {
                return 1.0 - (hoursDifference / (double) fibHour) * 0.3;
            }
        }
        
        // Apply Golden Ratio decay for distant timing
        return Math.max(0.1, 1.0 / (1.0 + hoursDifference / GOLDEN_RATIO));
    }
    
    /**
     * Calculate weighted resonance using sacred mathematics
     */
    private double calculateWeightedResonance(
            double geneKeysCompatibility,
            double consciousnessLevelSynergy,
            double businessTrackAlignment,
            double synchronicityFactor) {
        
        // Sacred weighting using Fibonacci proportions
        double geneKeysWeight = 0.4;      // 40% - Most important
        double consciousnessWeight = 0.3; // 30% - Very important
        double businessWeight = 0.2;      // 20% - Important
        double syncWeight = 0.1;          // 10% - Timing bonus
        
        double weightedSum = 
            geneKeysCompatibility * geneKeysWeight +
            consciousnessLevelSynergy * consciousnessWeight +
            businessTrackAlignment * businessWeight +
            synchronicityFactor * syncWeight;
        
        // Apply Golden Ratio enhancement for high overall compatibility
        if (weightedSum > 0.8) {
            weightedSum = Math.min(1.0, weightedSum * 1.1);
        }
        
        return Math.round(weightedSum * 1000.0) / 1000.0; // 3 decimal precision
    }
    
    /**
     * Determine resonance level category
     */
    private ResonanceLevel determineResonanceLevel(double resonance) {
        if (resonance >= 0.9) return ResonanceLevel.SOUL_FAMILY;
        if (resonance >= 0.8) return ResonanceLevel.HIGH_RESONANCE;
        if (resonance >= 0.6) return ResonanceLevel.MEDIUM_RESONANCE;
        if (resonance >= 0.4) return ResonanceLevel.LOW_RESONANCE;
        return ResonanceLevel.MINIMAL_RESONANCE;
    }
    
    // Utility methods
    private Map<String, Integer> calculateRingDistribution(Set<String> geneKeys) {
        // This would query actual Codon Ring data
        Map<String, Integer> distribution = new HashMap<>();
        // Simplified implementation
        return distribution;
    }
    
    private Set<String> extractBusinessTracks(SyntopiaUser user) {
        // Extract business tracks from user roles  
        return user.getRoles().stream()
                .collect(Collectors.toSet());
    }
    
    private double calculateComplementaryTrackBonus(Set<String> tracks1, Set<String> tracks2) {
        // Define complementary track relationships
        Map<String, Set<String>> complementaryTracks = Map.of(
            "TECH_DEVELOPER", Set.of("UX_DESIGNER", "DATA_SCIENTIST"),
            "BUSINESS_DEVELOPER", Set.of("LEGAL_ADVISOR", "FINANCE_ANALYST"),
            "SUSTAINABILITY_LEAD", Set.of("DATA_SCIENTIST", "BUSINESS_DEVELOPER")
        );
        
        double bonus = 0.0;
        for (String track1 : tracks1) {
            if (complementaryTracks.containsKey(track1)) {
                for (String track2 : tracks2) {
                    if (complementaryTracks.get(track1).contains(track2)) {
                        bonus += 0.1;
                    }
                }
            }
        }
        
        return Math.min(0.3, bonus); // Max 30% bonus
    }
    
    private ResonanceConnection mapToResonanceConnection(ConsciousnessResonance resonance) {
        return ResonanceConnection.builder()
                .userId(resonance.getTo())
                .resonanceStrength(resonance.getResonanceStrength())
                .connectionType("consciousness_resonance")
                .lastCalculated(resonance.getLastCalculated())
                .build();
    }
    
    // Data classes
    public enum ResonanceLevel {
        SOUL_FAMILY, HIGH_RESONANCE, MEDIUM_RESONANCE, LOW_RESONANCE, MINIMAL_RESONANCE
    }
    
    public static class ResonanceResult {
        public final String user1Id, user2Id;
        public final double totalResonance, geneKeysCompatibility, consciousnessLevelSynergy;
        public final double businessTrackAlignment, synchronicityFactor;
        public final ResonanceLevel resonanceLevel;
        public final LocalDateTime calculatedAt;
        
        private ResonanceResult(Builder builder) {
            this.user1Id = builder.user1Id;
            this.user2Id = builder.user2Id;
            this.totalResonance = builder.totalResonance;
            this.geneKeysCompatibility = builder.geneKeysCompatibility;
            this.consciousnessLevelSynergy = builder.consciousnessLevelSynergy;
            this.businessTrackAlignment = builder.businessTrackAlignment;
            this.synchronicityFactor = builder.synchronicityFactor;
            this.resonanceLevel = builder.resonanceLevel;
            this.calculatedAt = builder.calculatedAt;
        }
        
        public static Builder builder() { return new Builder(); }
        
        public static class Builder {
            private String user1Id, user2Id;
            private double totalResonance, geneKeysCompatibility, consciousnessLevelSynergy;
            private double businessTrackAlignment, synchronicityFactor;
            private ResonanceLevel resonanceLevel;
            private LocalDateTime calculatedAt;
            
            public Builder user1Id(String user1Id) { this.user1Id = user1Id; return this; }
            public Builder user2Id(String user2Id) { this.user2Id = user2Id; return this; }
            public Builder totalResonance(double totalResonance) { this.totalResonance = totalResonance; return this; }
            public Builder geneKeysCompatibility(double geneKeysCompatibility) { this.geneKeysCompatibility = geneKeysCompatibility; return this; }
            public Builder consciousnessLevelSynergy(double consciousnessLevelSynergy) { this.consciousnessLevelSynergy = consciousnessLevelSynergy; return this; }
            public Builder businessTrackAlignment(double businessTrackAlignment) { this.businessTrackAlignment = businessTrackAlignment; return this; }
            public Builder synchronicityFactor(double synchronicityFactor) { this.synchronicityFactor = synchronicityFactor; return this; }
            public Builder resonanceLevel(ResonanceLevel resonanceLevel) { this.resonanceLevel = resonanceLevel; return this; }
            public Builder calculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; return this; }
            public ResonanceResult build() { return new ResonanceResult(this); }
        }
    }
    
    public static class ResonanceConnection {
        public final String userId;
        public final double resonanceStrength;
        public final String connectionType;
        public final LocalDateTime lastCalculated;
        
        private ResonanceConnection(Builder builder) {
            this.userId = builder.userId;
            this.resonanceStrength = builder.resonanceStrength;
            this.connectionType = builder.connectionType;
            this.lastCalculated = builder.lastCalculated;
        }
        
        // Getter methods for sorting
        public double getResonanceStrength() {
            return resonanceStrength;
        }
        
        public String getUserId() {
            return userId;
        }
        
        public String getConnectionType() {
            return connectionType;
        }
        
        public LocalDateTime getLastCalculated() {
            return lastCalculated;
        }
        
        public static Builder builder() { return new Builder(); }
        
        public static class Builder {
            private String userId, connectionType;
            private double resonanceStrength;
            private LocalDateTime lastCalculated;
            
            public Builder userId(String userId) { this.userId = userId; return this; }
            public Builder resonanceStrength(double resonanceStrength) { this.resonanceStrength = resonanceStrength; return this; }
            public Builder connectionType(String connectionType) { this.connectionType = connectionType; return this; }
            public Builder lastCalculated(LocalDateTime lastCalculated) { this.lastCalculated = lastCalculated; return this; }
            public ResonanceConnection build() { return new ResonanceConnection(this); }
        }
    }
    
    // Exception class
    public static class ResonanceCalculationException extends RuntimeException {
        public ResonanceCalculationException(String message) { super(message); }
        public ResonanceCalculationException(String message, Throwable cause) { super(message, cause); }
    }
}
