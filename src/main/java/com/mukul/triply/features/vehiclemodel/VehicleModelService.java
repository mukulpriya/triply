package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseService;
import com.mukul.triply.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Year;
import java.util.Optional;

@Service
public class VehicleModelService extends BaseService<VehicleModelEntity, VehicleModelEntry> {

    private final VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleModelService(final VehicleModelRepository repository) {
        super();
        this.repository = repository;
        this.vehicleModelRepository = repository;
        this.mapper = VehicleModelMapper.INSTANCE;
    }

    public VehicleModelEntry CreateBy(final String name, final String brand, final VehicleType vehicleType, final Year make, final Double emissionPerKm, final Double runningCostPerKm) {
        final VehicleModelEntity vehicleModelEntity = new VehicleModelEntity();
        vehicleModelEntity.setName(name);
        vehicleModelEntity.setBrand(brand);
        vehicleModelEntity.setType(vehicleType);
        vehicleModelEntity.setMake(make);
        vehicleModelEntity.setEmissionPerKm(emissionPerKm);
        vehicleModelEntity.setRunningCostPerKm(runningCostPerKm);
        final Optional<VehicleModelEntity> optionalVehicleModel = vehicleModelRepository.findByNameAndBrandAndTypeAndMake(name, brand, vehicleType, make);
        if (optionalVehicleModel.isPresent()) {
            return this.mapper.toEntry(optionalVehicleModel.get());
        }
        return this.mapper.toEntry(vehicleModelRepository.save(vehicleModelEntity));
    }

    public VehicleModelEntry getBy(final String name, final String brand, final VehicleType vehicleType, final Year make) throws NotFoundException {
        final Optional<VehicleModelEntity> optionalVehicleModel = vehicleModelRepository.findByNameAndBrandAndTypeAndMake(name, brand, vehicleType, make);
        if (optionalVehicleModel.isPresent()) {
            return this.mapper.toEntry(optionalVehicleModel.get());
        } else
            throw new NotFoundException("Vehicle Model Not Found");
    }
}
