package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseResponse;
import com.mukul.triply.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-models")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VehicleModelController {

    private final VehicleModelFacade facade;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<BaseResponse<VehicleModelEntry>> uploadVehicleModels(@RequestParam("file") final MultipartFile file) throws IOException, NotFoundException {
        final List<VehicleModelEntry> vehicleModels = facade.handleVehicleModelUpload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(vehicleModels));
    }

}
