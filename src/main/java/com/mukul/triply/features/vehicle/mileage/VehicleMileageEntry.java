package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseEntry;
import com.mukul.triply.features.user.UserEmissionEntry;
import com.mukul.triply.features.vehicle.VehicleEntity;
import com.mukul.triply.features.vehicle.VehicleEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMileageEntry extends BaseEntry {
    private Long id;

    private Year year;

    private Month month;

    private Integer week;

    private Double totalEmission;

    private Double totalRunningCost;

    private Double distanceTravelledInKm;

    private Boolean isValid;

    public static VehicleMileageEntry getVehicleMileageEntry(UserEmissionEntry entry, VehicleEntity vehicleEntity) {
        final VehicleMileageEntry mileage = new VehicleMileageEntry();
        mileage.setYear(entry.getYear());
        mileage.setMonth(entry.getMonth());
        mileage.setWeek(entry.getWeek());
        mileage.setDistanceTravelledInKm(entry.getDistanceTravelledInKm());
        mileage.setTotalEmission(entry.getDistanceTravelledInKm() * vehicleEntity.getVehicleModel().getEmissionPerKm());
        mileage.setTotalRunningCost(entry.getDistanceTravelledInKm() * vehicleEntity.getVehicleModel().getRunningCostPerKm());
        mileage.setIsValid(Boolean.TRUE);
        return mileage;
    }
}
