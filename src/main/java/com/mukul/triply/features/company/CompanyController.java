package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseRequest;
import com.mukul.triply.common.baseclasses.BaseResponse;
import com.mukul.triply.config.security.HasRole;
import com.mukul.triply.config.security.SecurityContext;
import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.exception.UnauthorizedException;
import com.mukul.triply.features.user.UserEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyController {

    private final CompanyFacade facade;

    @HasRole("SUPER_ADMIN")
    @PostMapping("")
    public ResponseEntity<BaseResponse<CompanyEntry>> createCompany(@RequestBody BaseRequest<CompanyEntry> createCompanyRequest) {
        final CompanyEntry companyEntry = facade.createCompany(createCompanyRequest.getEntry());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(companyEntry));
    }

    @HasRole({"SUPER_ADMIN", "ADMIN"})
    @PostMapping(value = "/{companyId}/users/upload", consumes = "multipart/form-data")
    public ResponseEntity<BaseResponse<UserEntry>> uploadEmployees(@PathVariable("companyId") final String companyId, @RequestParam("file") final MultipartFile file) throws IOException, NotFoundException, UnauthorizedException {
        if (SecurityContext.getRoles().contains("SUPER_ADMIN") || (SecurityContext.getLoggedInUserCompanyId().equals(companyId) && SecurityContext.getRoles().contains("ADMIN"))) {
            final List<UserEntry> users = facade.handleUserUpload(companyId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(users));
        } else {
            throw new UnauthorizedException("You cannot upload employees for this company.");
        }
    }

    @HasRole("ADMIN")
    @GetMapping("/{companyId}/emissions")
    public ResponseEntity<List<CompanyEmissionEntry>> getEmissions(@PathVariable("companyId") final String companyId) {
        List<CompanyEmissionEntry> emissions = facade.getCompanyEmissions(companyId);
        return ResponseEntity.ok(emissions);
    }
}
