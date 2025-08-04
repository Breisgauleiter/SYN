package org.syntopia.consciousness.platform.dto.auth;

/**
 * Sacred Authentication Response DTO
 */
public class AuthResponseDto {
    
    private UserDto user;
    private TokenResponseDto tokens;
    private String message;
    private boolean success = true;
    
    // Constructors
    public AuthResponseDto() {}
    
    public AuthResponseDto(UserDto user, TokenResponseDto tokens, String message) {
        this.user = user;
        this.tokens = tokens;
        this.message = message;
    }
    
    // Getters and Setters
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    
    public TokenResponseDto getTokens() { return tokens; }
    public void setTokens(TokenResponseDto tokens) { this.tokens = tokens; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
