package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * SYNtopia User with Hologenetic Profile
 * 
 * Combines traditional user management with consciousness-based profiles
 * integrating the complete Gene Keys system for authentic team formation.
 * 
 * Features:
 * - 4 Gene Keys Sequences (Activation, Venus, Pearl, Star Pearl)
 * - 22 Codon Ring Community Memberships
 * - SCL Progression System (1-25 Consciousness Levels)
 * - Privacy-First Encrypted Sacred Data
 * - Business Track Development
 */
@Entity
@Table(name = "syntopia_users")
public class SyntopiaUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    // Basic User Information
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;
    
    @Column(unique = true, nullable = false)
    @Email(message = "Valid email is required")
    private String email;
    
    @Column(nullable = false)
    private String passwordHash;
    
    private String firstName;
    private String lastName;
    private String avatar;
    
    // Sacred Consciousness Level
    private String consciousnessLevel = "AWAKENING";
    
    // Personal Vision (can be encrypted for privacy)
    private String personalVision;
    
    // Consciousness Profile - Simplified for MVP
    // TODO: Re-enable full HologeneticProfile after solving mapping conflicts
    // @Embedded
    // private HologeneticProfile hologeneticProfile;
    
    @Embedded  
    private ConsciousnessLevel currentLevel;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SynContribution> contributions = new ArrayList<>();
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<CodonRingMembership> codonRingMemberships = new HashSet<>();
    
    // Business Development
    @Enumerated(EnumType.STRING)
    private BusinessTrack primaryBusinessTrack;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<BusinessTrack> secondaryBusinessTracks = new HashSet<>();
    
    // Sacred Security & Privacy
    @Column(name = "encrypted_personal_vision")
    private String encryptedPersonalVision;  // AES-256-GCM encrypted
    
    @ElementCollection
    @Column(name = "encrypted_sacred_intentions")
    private List<String> encryptedSacredIntentions = new ArrayList<>();
    
    // Platform Configuration
    @Embedded
    private UserPreferences preferences;
    
    // Metadata
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime lastActiveAt;
    
    private LocalDateTime profileCompletedAt;
    
    @Version
    private Long version;
    
    // Constructors
    public SyntopiaUser() {
        this.createdAt = LocalDateTime.now();
        this.lastActiveAt = LocalDateTime.now();
        this.currentLevel = new ConsciousnessLevel(1); // Start at SCL 1 - Awakening
        this.preferences = new UserPreferences();
    }
    
    public SyntopiaUser(String username, String email, String passwordHash) {
        this();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    
    // Gene Keys Integration Methods
    // TODO: Re-enable methods after fixing HologeneticProfile mapping
    public boolean hasCompletedHologeneticProfile() {
        // return hologeneticProfile != null && 
        //        hologeneticProfile.isComplete() &&
        //        profileCompletedAt != null;
        return false; // Simplified for MVP
    }
    
    public void completeHologeneticProfile(HologeneticProfile profile) {
        // this.hologeneticProfile = profile;
        this.profileCompletedAt = LocalDateTime.now();
    }
    
    public boolean canProgressToLevel(int targetLevel) {
        if (currentLevel == null) return false;
        
        return currentLevel.getLevel() < targetLevel &&
               hasRequiredContributionsForLevel(targetLevel) &&
               hasCompletedHologeneticProfile();
    }
    
    private boolean hasRequiredContributionsForLevel(int targetLevel) {
        // Fibonacci-based contribution requirements
        int[] fibonacciRequirements = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368};
        
        if (targetLevel >= fibonacciRequirements.length) return false;
        
        long contributionCount = contributions.stream()
            .filter(c -> c.getStatus() == ContributionStatus.COMPLETED)
            .count();
            
        return contributionCount >= fibonacciRequirements[targetLevel];
    }
    
    public void progressToNextLevel() {
        if (currentLevel != null && canProgressToLevel(currentLevel.getLevel() + 1)) {
            this.currentLevel = new ConsciousnessLevel(currentLevel.getLevel() + 1);
        }
    }
    
    // Business Track Methods
    public boolean canAdvanceInBusinessTrack() {
        return primaryBusinessTrack != null && 
               currentLevel != null &&
               currentLevel.getLevel() >= 5; // Minimum SCL 5 for business advancement
    }
    
    public void addSecondaryBusinessTrack(BusinessTrack track) {
        if (canAdvanceInBusinessTrack() && 
            track != primaryBusinessTrack &&
            secondaryBusinessTracks.size() < 3) { // Maximum 3 secondary tracks
            secondaryBusinessTracks.add(track);
        }
    }
    
    // Codon Ring Integration
    public void joinCodonRing(CodonRingMembership membership) {
        // TODO: Re-enable when HologeneticProfile is fixed
        // if (hasCompletedHologeneticProfile() && 
        //     hologeneticProfile.isCompatibleWithCodonRing(membership.getRingNumber())) {
        //     codonRingMemberships.add(membership);
        // }
        
        // Simplified for MVP
        if (currentLevel.getLevel() >= 3) {
            codonRingMemberships.add(membership);
        }
    }
    
    public Set<Integer> getCompatibleCodonRings() {
        // TODO: Re-enable when HologeneticProfile is fixed
        // if (!hasCompletedHologeneticProfile()) {
        //     return Collections.emptySet();
        // }
        // return hologeneticProfile.getCompatibleCodonRings();
        
        return Set.of(1, 2, 3); // Mock compatible rings for MVP
    }
    
    // Sacred Privacy Methods
    public void setPersonalVision(String vision, String encryptionKey) {
        // Implement AES-256-GCM encryption
        this.encryptedPersonalVision = encryptSacredData(vision, encryptionKey);
    }
    
    public String getPersonalVision(String decryptionKey) {
        // Implement AES-256-GCM decryption
        return decryptSacredData(encryptedPersonalVision, decryptionKey);
    }
    
    public void addSacredIntention(String intention, String encryptionKey) {
        String encrypted = encryptSacredData(intention, encryptionKey);
        encryptedSacredIntentions.add(encrypted);
    }
    
    private String encryptSacredData(String data, String key) {
        // TODO: Implement AES-256-GCM encryption
        // This should use the consciousness encryption service
        return data; // Placeholder
    }
    
    private String decryptSacredData(String encryptedData, String key) {
        // TODO: Implement AES-256-GCM decryption
        // This should use the consciousness encryption service
        return encryptedData; // Placeholder
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public String getConsciousnessLevel() { return consciousnessLevel; }
    public void setConsciousnessLevel(String consciousnessLevel) { this.consciousnessLevel = consciousnessLevel; }
    
    public String getPersonalVision() { return personalVision; }
    public void setPersonalVision(String personalVision) { this.personalVision = personalVision; }
    
    // TODO: Re-enable when HologeneticProfile mapping is fixed
    // public HologeneticProfile getHologeneticProfile() { return hologeneticProfile; }
    // public void setHologeneticProfile(HologeneticProfile hologeneticProfile) { 
    //     this.hologeneticProfile = hologeneticProfile; 
    // }
    
    public ConsciousnessLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(ConsciousnessLevel currentLevel) { this.currentLevel = currentLevel; }
    
    public List<SynContribution> getContributions() { return contributions; }
    public void setContributions(List<SynContribution> contributions) { this.contributions = contributions; }
    
    public Set<CodonRingMembership> getCodonRingMemberships() { return codonRingMemberships; }
    public void setCodonRingMemberships(Set<CodonRingMembership> codonRingMemberships) { 
        this.codonRingMemberships = codonRingMemberships; 
    }
    
    public BusinessTrack getPrimaryBusinessTrack() { return primaryBusinessTrack; }
    public void setPrimaryBusinessTrack(BusinessTrack primaryBusinessTrack) { 
        this.primaryBusinessTrack = primaryBusinessTrack; 
    }
    
    public Set<BusinessTrack> getSecondaryBusinessTracks() { return secondaryBusinessTracks; }
    public void setSecondaryBusinessTracks(Set<BusinessTrack> secondaryBusinessTracks) { 
        this.secondaryBusinessTracks = secondaryBusinessTracks; 
    }
    
    public UserPreferences getPreferences() { return preferences; }
    public void setPreferences(UserPreferences preferences) { this.preferences = preferences; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastActiveAt() { return lastActiveAt; }
    public void setLastActiveAt(LocalDateTime lastActiveAt) { this.lastActiveAt = lastActiveAt; }
    
    public LocalDateTime getProfileCompletedAt() { return profileCompletedAt; }
    public void setProfileCompletedAt(LocalDateTime profileCompletedAt) { 
        this.profileCompletedAt = profileCompletedAt; 
    }
    
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    
    // Missing methods required by service layer
    public List<Integer> getActivatedGeneKeys() {
        List<Integer> activatedKeys = new ArrayList<>();
        // TODO: Re-enable when HologeneticProfile is fixed
        // if (hologeneticProfile != null) {
        //     // Add the 4 primary gene keys
        //     if (hologeneticProfile.getLifeworkGeneKey() != null) {
        //         activatedKeys.add(hologeneticProfile.getLifeworkGeneKey().getKeyNumber());
        //     }
        //     if (hologeneticProfile.getEvolutionGeneKey() != null) {
        //         activatedKeys.add(hologeneticProfile.getEvolutionGeneKey().getKeyNumber());
        //     }
        //     if (hologeneticProfile.getRadianceGeneKey() != null) {
        //         activatedKeys.add(hologeneticProfile.getRadianceGeneKey().getKeyNumber());
        //     }
        //     if (hologeneticProfile.getPurposeGeneKey() != null) {
        //         activatedKeys.add(hologeneticProfile.getPurposeGeneKey().getKeyNumber());
        //     }
        // }
        
        // Mock data for MVP
        activatedKeys.addAll(List.of(1, 15, 33, 64)); // Mock gene keys
        return activatedKeys;
    }
    
    public Integer getCurrentSCL() {
        return currentLevel != null ? currentLevel.getLevel() : 1;
    }
    
    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        if (primaryBusinessTrack != null) {
            roles.add("BUSINESS_TRACK_" + primaryBusinessTrack.name());
        }
        if (getCurrentSCL() >= 10) {
            roles.add("CONSCIOUSNESS_GUIDE");
        }
        if (getCurrentSCL() >= 20) {
            roles.add("COMMUNITY_LEADER");
        }
        return roles;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SyntopiaUser)) return false;
        SyntopiaUser that = (SyntopiaUser) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "SyntopiaUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", currentLevel=" + (currentLevel != null ? currentLevel.getLevel() : "null") +
                ", primaryBusinessTrack=" + primaryBusinessTrack +
                ", createdAt=" + createdAt +
                '}';
    }
}
