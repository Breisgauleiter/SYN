package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * User's Membership in a Codon Ring Community
 * 
 * Represents a user's active participation in one of the 22 Codon Rings
 * for collaborative consciousness evolution.
 */
@Entity
@Table(name = "user_codon_ring_memberships")
public class UserCodonRingMembership {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @NotNull
    @Column(name = "user_id")
    private String userId;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "codon_ring")
    private CodonRingMembership codonRing;
    
    @NotNull
    @Column(name = "primary_gene_key")
    private Integer primaryGeneKey;
    
    @NotNull
    @Column(name = "membership_activated_at")
    private LocalDateTime membershipActivatedAt;
    
    @Column(name = "consciousness_level_at_activation")
    private Integer consciousnessLevelAtActivation;
    
    @Column(name = "is_ring_leader")
    private Boolean isRingLeader = false;
    
    @Column(name = "leadership_activated_at")
    private LocalDateTime leadershipActivatedAt;
    
    // Constructors
    public UserCodonRingMembership() {}
    
    public UserCodonRingMembership(String userId, CodonRingMembership codonRing, Integer primaryGeneKey) {
        this.userId = userId;
        this.codonRing = codonRing;
        this.primaryGeneKey = primaryGeneKey;
        this.membershipActivatedAt = LocalDateTime.now();
        this.isRingLeader = false;
    }
    
    // Business Logic
    public boolean canBecomeLeader(Integer currentSCL) {
        return currentSCL >= 15 && !isRingLeader;
    }
    
    public void activateLeadership(Integer currentSCL) {
        if (canBecomeLeader(currentSCL)) {
            this.isRingLeader = true;
            this.leadershipActivatedAt = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public CodonRingMembership getCodonRing() { return codonRing; }
    public void setCodonRing(CodonRingMembership codonRing) { this.codonRing = codonRing; }
    
    public Integer getPrimaryGeneKey() { return primaryGeneKey; }
    public void setPrimaryGeneKey(Integer primaryGeneKey) { this.primaryGeneKey = primaryGeneKey; }
    
    public LocalDateTime getMembershipActivatedAt() { return membershipActivatedAt; }
    public void setMembershipActivatedAt(LocalDateTime membershipActivatedAt) { 
        this.membershipActivatedAt = membershipActivatedAt; 
    }
    
    public Integer getConsciousnessLevelAtActivation() { return consciousnessLevelAtActivation; }
    public void setConsciousnessLevelAtActivation(Integer consciousnessLevelAtActivation) { 
        this.consciousnessLevelAtActivation = consciousnessLevelAtActivation; 
    }
    
    public Boolean getIsRingLeader() { return isRingLeader; }
    public void setIsRingLeader(Boolean isRingLeader) { this.isRingLeader = isRingLeader; }
    
    public LocalDateTime getLeadershipActivatedAt() { return leadershipActivatedAt; }
    public void setLeadershipActivatedAt(LocalDateTime leadershipActivatedAt) { 
        this.leadershipActivatedAt = leadershipActivatedAt; 
    }
}
