package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModelEntry extends BaseEntry {
    private String id;

    private String name;

    private String brand;

    private VehicleType type;

    private Year make;

    private Double emissionPerKm;

    private Double runningCostPerKm;
}
