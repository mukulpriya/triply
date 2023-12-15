package com.mukul.triply.features.role;

import com.mukul.triply.common.baseclasses.BaseEntity;
import com.mukul.triply.features.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<UserEntity> users;
}
