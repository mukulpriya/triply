package com.mukul.triply.features.user.token;

import com.mukul.triply.features.role.RoleEntry;
import com.mukul.triply.features.user.UserEntry;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TokenService {

    @Value("${jwt.access.secret_key:c2VjcmV0}")
    private String secretKey;

    @Value("${jwt.access.token_expiry_ms:3600000}")
    private long accessTokenExpiryInMs;

    @Value("${jwt.refresh.token_expiry_ms:3600000}")
    private long refreshTokenExpiryInMs;

    private final TokenRepository tokenRepository;

    public Pair<String, String> generateTokenPair(final UserEntry user) {
        final Instant now = Instant.now();
        final Instant accessTokenValidity = now.plusMillis(accessTokenExpiryInMs);
        final Instant refreshTokenValidity = now.plusMillis(refreshTokenExpiryInMs);
        final List<String> roles = user.getRoles().stream().map(RoleEntry::getName).distinct().toList();

        final String accessToken = Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("companyId", user.getCompany().getId())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(accessTokenValidity))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        final TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(user.getId());
        tokenEntity.setExpiresAt(refreshTokenValidity);
        tokenEntity.setAccessToken(accessToken);
        final TokenEntity savedToken = tokenRepository.save(tokenEntity);

        return Pair.of(accessToken, savedToken.getId());
    }

    public Optional<TokenEntry> findByRefreshToken(final String refreshToken) {
        return tokenRepository.findById(refreshToken).map(TokenEntry::new);
    }

    public Claims getClaims(final String accessToken) {
        final JwtParser parser = getJwtParser();
        return parser.parseSignedClaims(accessToken).getPayload();
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().setSigningKey(secretKey)
                .build();
    }
}
