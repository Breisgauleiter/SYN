package org.syntopia.consciousness.platform.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Sacred Profile Creation Request DTO
 */
public class ProfileCreationRequest {
    
    @NotNull
    private LocalDateTime birthDateTime;
    
    @NotNull  
    private String birthLocation;
    
    private String birthTimezone = "UTC";
    
    // Constructors
    public ProfileCreationRequest() {}
    
    public ProfileCreationRequest(LocalDateTime birthDateTime, String birthLocation) {
        this.birthDateTime = birthDateTime;
        this.birthLocation = birthLocation;
    }
    
    // Getters and setters
    public LocalDateTime getBirthDateTime() { return birthDateTime; }
    public void setBirthDateTime(LocalDateTime birthDateTime) { this.birthDateTime = birthDateTime; }
    
    public String getBirthLocation() { return birthLocation; }
    public void setBirthLocation(String birthLocation) { this.birthLocation = birthLocation; }
    
    public String getBirthTimezone() { return birthTimezone; }
    public void setBirthTimezone(String birthTimezone) { this.birthTimezone = birthTimezone; }
}


