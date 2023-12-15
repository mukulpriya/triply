package com.mukul.triply.features.user;

import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.exception.UnauthenticatedException;
import com.mukul.triply.features.user.token.TokenEntry;
import com.mukul.triply.features.user.token.TokenService;
import com.mukul.triply.features.vehicle.VehicleEntry;
import com.mukul.triply.features.vehicle.VehicleService;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageEntry;
import com.mukul.triply.features.vehicle.mileage.VehicleMileageService;
import com.mukul.triply.features.vehiclemodel.VehicleModelEntry;
import com.mukul.triply.features.vehiclemodel.VehicleModelService;
import com.mukul.triply.features.vehiclemodel.VehicleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class UserFacade {

    private final UserService userService;

    private final VehicleService vehicleService;

    private final VehicleModelService vehicleModelService;

    private final TokenService tokenService;

    private final VehicleMileageService vehicleMileageService;

    public void uploadUserEmissions(final Year year, final Month month, final MultipartFile file) throws IOException, NotFoundException {
        final List<UserEmissionEntry> emissionEntries = parseFile(year, month, file);
        for (final UserEmissionEntry emission : emissionEntries) {
            upsertUserEmissions(emission);
        }
    }

    private List<UserEmissionEntry> parseFile(final Year year, final Month month, MultipartFile file) throws IOException {
        List<UserEmissionEntry> emissionDataList = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    UserEmissionEntry emissionEntry = new UserEmissionEntry();
                    emissionEntry.setEmployeeId(data[0].trim());
                    emissionEntry.setRegistrationNumber(data[1].trim());
                    emissionEntry.setVehicleName(data[2].trim());
                    emissionEntry.setVehicleBrand(data[3].trim());
                    emissionEntry.setVehicleMake(Year.parse(data[4].trim()));
                    emissionEntry.setVehicleType(VehicleType.fromString(data[5]));
                    emissionEntry.setYear(year);
                    emissionEntry.setMonth(month);
                    emissionEntry.setWeek(Integer.parseInt(data[6].trim()));
                    emissionEntry.setDistanceTravelledInKm(Double.parseDouble(data[7].trim()));
                    emissionDataList.add(emissionEntry);
                } else {
                    throw new BadRequestException("not enough fields");
                }
            }
        }

        return emissionDataList;
    }

    private void upsertUserEmissions(final UserEmissionEntry entry) throws BadRequestException, NotFoundException {
        final Optional<UserEntry> optionalUserEntry = userService.findByEmployeeId(entry.getEmployeeId());
        if (optionalUserEntry.isEmpty()) {
            throw new BadRequestException("user not found fields");
        }
        final UserEntry userEntry = optionalUserEntry.get();
        final VehicleModelEntry vehicleModelEntry = vehicleModelService.getBy(entry.getVehicleName(), entry.getVehicleBrand(), entry.getVehicleType(), entry.getVehicleMake());
        final VehicleEntry vehicleEntry = vehicleService.getOrCreateBy(userEntry, vehicleModelEntry, entry.getRegistrationNumber());
        final VehicleMileageEntry mileage = VehicleMileageEntry.getVehicleMileageEntry(entry, vehicleEntry);

        vehicleService.addOrUpdateMileage(vehicleEntry.getId(), mileage);
    }

    public TokenEntry login(final String email, final String password) throws BadRequestException {
        final Optional<UserEntry> user = userService.findByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            throw new BadRequestException("No user found with email :: " + email);
        }
        final UserEntry userEntry = user.get();
        final Pair<String, String> tokens = tokenService.generateTokenPair(userEntry);
        return new TokenEntry(userEntry, tokens.getFirst(), tokens.getSecond());
    }

    public TokenEntry refreshToken(final String refreshToken, final String accessToken) throws UnauthenticatedException, BadRequestException {
        final Optional<TokenEntry> optionalToken = tokenService.findByRefreshToken(refreshToken);
        if (optionalToken.isEmpty()) {
            throw new UnauthenticatedException("Refresh token not found.");
        }
        final TokenEntry token = optionalToken.get();
        if (!Objects.equals(token.getAccessToken(), accessToken)) {
            throw new UnauthenticatedException("Access token doesn't match.");
        }
        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new UnauthenticatedException("Refresh token is expired");
        }
        final UserEntry userEntry = userService.read(token.getUserId());
        final Pair<String, String> tokens = tokenService.generateTokenPair(userEntry);
        return new TokenEntry(userEntry, tokens.getFirst(), tokens.getSecond());
    }
}
