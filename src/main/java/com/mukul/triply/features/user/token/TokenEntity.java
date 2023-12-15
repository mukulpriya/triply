package com.mukul.triply.features.user.token;

import com.mukul.triply.common.baseclasses.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tokens")
@Data
public class TokenEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    private String userId;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    private Instant expiresAt;
}
