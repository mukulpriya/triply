package com.mukul.triply.features.user;

import com.mukul.triply.common.baseclasses.BaseEntity;
import com.mukul.triply.features.company.CompanyEntity;
import com.mukul.triply.features.role.RoleEntity;
import com.mukul.triply.features.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    private String email;

    @Column(name = "employee_id")
    private String employeeId;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<VehicleEntity> vehicles;
}
