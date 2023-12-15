package com.mukul.triply.features.vehiclemodel;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VehicleType {
    PETROL("PETROL"),
    DIESEL("DIESEL"),
    HYBRID("HYBRID"),
    ELECTRIC("ELECTRIC"),
    ETHANOL_LOVED("ETHANOL_LOVED");

    private final String type;

    VehicleType(final String value) {
        this.type = value;
    }

    public static VehicleType fromString(String typeName) {
        return Arrays.stream(VehicleType.values())
                .filter(type -> type.type.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(null);
    }


    @Override
    public String toString() {
        return type;
    }
}
