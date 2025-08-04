package org.syntopia.consciousness.platform.domain;

/**
 * SYN Contribution Status Tracking
 */
public enum ContributionStatus {
    PROPOSED("Proposed", "Initial idea or proposal"),
    IN_PROGRESS("In Progress", "Actively being worked on"),
    REVIEW("Under Review", "Awaiting review and approval"),
    COMPLETED("Completed", "Successfully finished and integrated"),
    ARCHIVED("Archived", "Completed but no longer active");
    
    private final String displayName;
    private final String description;
    
    ContributionStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
