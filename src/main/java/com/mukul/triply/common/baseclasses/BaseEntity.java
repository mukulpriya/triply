package com.mukul.triply.common.baseclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.mukul.triply.constant.Constants.BASE_DATE_TIME_FORMAT;
import static com.mukul.triply.constant.Constants.SYSTEM;

@Data
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by", updatable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BASE_DATE_TIME_FORMAT)
    @Column(name = "created_at", nullable = false, updatable = false)
    protected ZonedDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BASE_DATE_TIME_FORMAT)
    @Column(name = "updated_at", nullable = false)
    protected ZonedDateTime updatedAt;

    @Version
    @Column(name = "version")
    protected Long version = 0L;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "deleted_token")
    private String deletedToken;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = this.createdAt = Objects.isNull(this.createdAt) ? ZonedDateTime.now() : this.createdAt;
        this.updatedBy = this.createdBy = SYSTEM;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
        this.updatedBy = SYSTEM;
    }
}
