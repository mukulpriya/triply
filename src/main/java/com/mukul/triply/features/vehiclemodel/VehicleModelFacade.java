package com.mukul.triply.features.vehiclemodel;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelFacade {

    private final VehicleModelService vehicleModelService;

    public List<VehicleModelEntry> handleVehicleModelUpload(final MultipartFile file) throws IOException {
        final List<VehicleModelEntry> vehicleModelEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    final String name = data[0];
                    final String brand = data[1];
                    final Double emissionPerKm = Double.valueOf(data[3]);
                    final Double runningCostPerKm = Double.valueOf(data[4]);
                    final Year make = Year.parse(data[5]);
                    final VehicleType vehicleType = VehicleType.fromString(data[2]);
                    VehicleModelEntry vehicleModelEntry = vehicleModelService.CreateBy(name, brand, vehicleType, make, emissionPerKm, runningCostPerKm);
                    vehicleModelEntries.add(vehicleModelEntry);
                }
            }
        }
        return vehicleModelService.createAll(vehicleModelEntries);
    }
}