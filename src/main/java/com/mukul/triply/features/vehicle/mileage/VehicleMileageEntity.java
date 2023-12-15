package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseEntity;
import com.mukul.triply.features.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Month;
import java.time.Year;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vehicle_mileages")
@Data
public class VehicleMileageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    private Year year;

    @Enumerated(EnumType.STRING)
    private Month month;

    private Integer week;

    @Column(name = "total_emission")
    private Double totalEmission;

    @Column(name = "total_running_cost")
    private Double totalRunningCost;

    @Column(name = "distance_travelled_in_km")
    private Double distanceTravelledInKm;

    @Column(name = "is_valid")
    private Boolean isValid;
}
