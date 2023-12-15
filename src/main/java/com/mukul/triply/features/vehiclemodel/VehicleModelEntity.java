package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vehicle_models", uniqueConstraints = {
        @UniqueConstraint(name = "uk_name_brand_type_make", columnNames = {"name", "brand", "type", "make"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModelEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    private String name;

    private String brand;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private Year make;

    private Double emissionPerKm;

    private Double runningCostPerKm;
}
