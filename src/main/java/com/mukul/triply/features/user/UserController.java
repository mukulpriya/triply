package com.mukul.triply.features.user;

import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.exception.UnauthenticatedException;
import com.mukul.triply.features.user.token.TokenEntry;
import com.mukul.triply.features.vehicle.VehicleEntry;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    final UserFacade userFacade;

    @GetMapping("/emissions")
    public ResponseEntity<?> getEmissions() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/suggestions")
    public ResponseEntity<?> getReplacementSuggestions() {
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/emissions/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadEmissions(@RequestParam("year") Year year, @RequestParam("month") Month month, @RequestParam("file") MultipartFile file) throws NotFoundException, IOException {
        userFacade.uploadUserEmissions(year, month, file);
        return ResponseEntity.ok("Emissions uploaded successfully");

    }

    @PostMapping("/login")
    public ResponseEntity<TokenEntry> login(@RequestBody LoginEntry loginRequest) throws BadRequestException {
        final TokenEntry tokens = userFacade.login(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenEntry request) throws UnauthenticatedException, BadRequestException {
        final TokenEntry tokens = userFacade.refreshToken(request.getRefreshToken(), request.getAccessToken());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokens);
    }
}
