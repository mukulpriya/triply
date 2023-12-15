package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

@Service
public class VehicleMileageService extends BaseService<VehicleMileageEntity, VehicleMileageEntry> {

    private final VehicleMileageRepository vehicleMileageRepository;
    @Autowired
    public VehicleMileageService(final VehicleMileageRepository repository, VehicleMileageRepository vehicleMileageRepository) {
        super();
        this.vehicleMileageRepository = vehicleMileageRepository;
        this.repository = repository;
        this.mapper = VehicleMileageMapper.INSTANCE;
    }

    public Optional<VehicleMileageEntity> getBy(final String vehicleId, final Year year, final Month month, final Integer week) {
        return vehicleMileageRepository.findByVehicleIdAndYearAndMonthAndWeek(vehicleId, year, month, week);

    }
}
