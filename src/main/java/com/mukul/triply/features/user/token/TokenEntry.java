package com.mukul.triply.features.user.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mukul.triply.features.user.UserEntry;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenEntry {
    private String userId;

    private UserEntry user;

    private String accessToken;

    private String refreshToken;

    private Instant expiresAt;

    public TokenEntry(final TokenEntity token) {
        this.userId = token.getUserId();
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getAccessToken();
        this.expiresAt = token.getExpiresAt();
    }

    public TokenEntry(UserEntry user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
