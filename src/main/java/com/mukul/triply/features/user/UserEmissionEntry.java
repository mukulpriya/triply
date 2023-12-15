package com.mukul.triply.features.user;

import com.mukul.triply.features.vehiclemodel.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.time.Year;

@Data
@NoArgsConstructor
public class UserEmissionEntry {
    private String employeeId;
    private String registrationNumber;
    private String vehicleName;
    private String vehicleBrand;
    private Year vehicleMake;
    private VehicleType vehicleType;
    private Year year;
    private Month month;
    private Integer week;
    private Double totalEmission;
    private Double totalRunningTime;
    private Double distanceTravelledInKm;
}
