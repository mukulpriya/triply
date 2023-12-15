package com.mukul.triply.features.vehicle;

import com.mukul.triply.common.baseclasses.BaseService;
import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.features.user.UserEntry;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageEntity;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageEntry;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageMapper;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageService;
import com.mukul.triply.features.vehiclemodel.VehicleModelEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleService extends BaseService<VehicleEntity, VehicleEntry> {

    private final VehicleRepository vehicleRepository;
    private final VehicleMileageService vehicleMileageService;

    @Autowired
    public VehicleService(final VehicleRepository repository, VehicleMileageService vehicleMileageService) {
        super();
        this.vehicleMileageService = vehicleMileageService;
        this.repository = repository;
        this.vehicleRepository = repository;
        this.mapper = VehicleMapper.INSTANCE;
    }

    private Optional<VehicleEntry> findByRegistrationNumber(final String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber).map(this.mapper::toEntry);
    }

    public VehicleEntry getOrCreateBy(final UserEntry userEntry, final VehicleModelEntry vehicleModelEntry, final String registrationNumber) {
        Optional<VehicleEntry> optionalVehicleEntry = findByRegistrationNumber(registrationNumber);
        if (optionalVehicleEntry.isPresent()) {
            return optionalVehicleEntry.get();
        }
        final VehicleEntry vehicleEntry = new VehicleEntry();
        vehicleEntry.setUser(userEntry);
        vehicleEntry.setIsActive(true);
        vehicleEntry.setVehicleModel(vehicleModelEntry);
        vehicleEntry.setRegistrationNumber(registrationNumber);
        return create(vehicleEntry);
    }

    public void addOrUpdateMileage(final String vehicleId, final VehicleMileageEntry mileage) throws NotFoundException {
        final Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (optionalVehicle.isEmpty()) {
            throw new NotFoundException("vehicle not found");
        }
        final VehicleEntity vehicleEntity = optionalVehicle.get();

        if (vehicleEntity.getMileages() == null) {
            vehicleEntity.setMileages(new ArrayList<>());
        }

        final Optional<VehicleMileageEntity> optionalVehicleMileage = vehicleEntity.getMileages().stream().filter(vehicleMileageEntity -> Objects.equals(vehicleMileageEntity.getWeek(), mileage.getWeek()) && mileage.getYear() == vehicleMileageEntity.getYear() &&
                vehicleMileageEntity.getMonth() == mileage.getMonth()).findFirst();

        if (optionalVehicleMileage.isEmpty()) {
            final VehicleMileageEntity entity = VehicleMileageMapper.INSTANCE.toEntity(mileage);
            vehicleEntity.getMileages().add(entity);
        } else {
            final VehicleMileageEntity vehicleMileageEntity = optionalVehicleMileage.get();
            vehicleMileageEntity.setDistanceTravelledInKm(mileage.getDistanceTravelledInKm());
            vehicleMileageEntity.setTotalEmission(mileage.getTotalEmission());
            vehicleMileageEntity.setTotalRunningCost(mileage.getTotalRunningCost());
            vehicleMileageEntity.setVehicle(vehicleEntity);
        }

        VehicleEntity vehicleEntity1 =  this.vehicleRepository.save(vehicleEntity);
    }
}
