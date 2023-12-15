package com.mukul.triply.features.vehicle;

import com.mukul.triply.common.baseclasses.BaseEntry;
import com.mukul.triply.features.user.UserEntry;
import com.mukul.triply.features.vehiclemodel.VehicleModelEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntry extends BaseEntry {
    private String id;

    private String registrationNumber;

    private Boolean isActive;

    private UserEntry user;

    private VehicleModelEntry vehicleModel;
}
