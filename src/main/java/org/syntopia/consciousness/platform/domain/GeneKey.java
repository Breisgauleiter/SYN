package org.syntopia.consciousness.platform.domain;

import jakarta.persistence.*;

/**
 * Individual Gene Key representation within the 64 Gene Keys system
 * 
 * Each Gene Key has:
 * - Number (1-64) corresponding to I'Ching hexagrams
 * - Line (1-6) for specific life themes
 * - Shadow, Gift, and Siddhi frequencies for consciousness evolution
 */
@Entity
@Table(name = "gene_keys")
public class GeneKey {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "key_number", unique = true)
    private int keyNumber; // 1-64
    
    @Column(name = "line_number") 
    private int line; // 1-6
    
    private String shadowAspect; // Low frequency expression
    
    private String giftAspect; // Balanced frequency expression
    
    private String siddhiAspect; // High frequency divine expression
    
    // Constructors
    public GeneKey() {}
    
    public GeneKey(int keyNumber, int line) {
        this.keyNumber = keyNumber;
        this.line = line;
        loadAspectsFromDatabase(keyNumber);
    }
    
    public GeneKey(int keyNumber, int line, String shadowAspect, String giftAspect, String siddhiAspect) {
        this.keyNumber = keyNumber;
        this.line = line;
        this.shadowAspect = shadowAspect;
        this.giftAspect = giftAspect;
        this.siddhiAspect = siddhiAspect;
    }
    
    private void loadAspectsFromDatabase(int keyNumber) {
        // TODO: Load from Gene Keys reference database
        // For now, placeholder data
        switch (keyNumber) {
            case 1:
                this.shadowAspect = "Entropy";
                this.giftAspect = "Freshness";
                this.siddhiAspect = "Beauty";
                break;
            case 2:
                this.shadowAspect = "Disorientation";
                this.giftAspect = "Orientation";
                this.siddhiAspect = "Love";
                break;
            default:
                this.shadowAspect = "Unknown Shadow";
                this.giftAspect = "Unknown Gift";
                this.siddhiAspect = "Unknown Siddhi";
        }
    }
    
    // Utility Methods
    public boolean isComplementaryTo(GeneKey other) {
        // I'Ching complementary pairs
        return (this.keyNumber + other.keyNumber) == 65;
    }
    
    public boolean isInSameCodonRing(GeneKey other) {
        // TODO: Implement codon ring calculation
        return getCodonRing() == other.getCodonRing();
    }
    
    public int getCodonRing() {
        // TODO: Calculate which of the 22 codon rings this Gene Key belongs to
        return (keyNumber - 1) % 22 + 1; // Simplified calculation
    }
    
    public String getFullName() {
        return "Gene Key " + keyNumber + " - " + giftAspect;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public int getKeyNumber() { return keyNumber; }
    public void setKeyNumber(int keyNumber) { this.keyNumber = keyNumber; }
    
    public int getLine() { return line; }
    public void setLine(int line) { this.line = line; }
    
    public String getShadowAspect() { return shadowAspect; }
    public void setShadowAspect(String shadowAspect) { this.shadowAspect = shadowAspect; }
    
    public String getGiftAspect() { return giftAspect; }
    public void setGiftAspect(String giftAspect) { this.giftAspect = giftAspect; }
    
    public String getSiddhiAspect() { return siddhiAspect; }
    public void setSiddhiAspect(String siddhiAspect) { this.siddhiAspect = siddhiAspect; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneKey)) return false;
        GeneKey geneKey = (GeneKey) o;
        return keyNumber == geneKey.keyNumber && line == geneKey.line;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(keyNumber, line);
    }
    
    @Override
    public String toString() {
        return "GeneKey{" +
                "keyNumber=" + keyNumber +
                ", line=" + line +
                ", gift='" + giftAspect + '\'' +
                '}';
    }
}
