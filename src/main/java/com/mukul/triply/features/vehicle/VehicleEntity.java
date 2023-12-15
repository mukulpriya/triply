package com.mukul.triply.features.vehicle;

import com.mukul.triply.common.baseclasses.BaseEntity;
import com.mukul.triply.features.user.UserEntity;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageEntity;
import com.mukul.triply.features.vehiclemodel.VehicleModelEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vehicles")
@Data
public class VehicleEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModelEntity vehicleModel;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<VehicleMileageEntity> mileages;
}