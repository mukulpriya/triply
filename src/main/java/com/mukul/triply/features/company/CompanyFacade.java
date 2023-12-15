package com.mukul.triply.features.company;

import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.features.role.RoleEntry;
import com.mukul.triply.features.role.RoleService;
import com.mukul.triply.features.user.UserEntry;
import com.mukul.triply.features.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyFacade {
    private final CompanyService companyService;

    private final UserService userService;

    private final RoleService roleService;

    public CompanyEntry createCompany(final CompanyEntry entry) {
        return companyService.create(entry);
    }

    public List<UserEntry> handleUserUpload(final String companyId, final MultipartFile file) throws IOException, NotFoundException {
        final CompanyEntry companyEntry = companyService.read(companyId);
        final List<UserEntry> userEntries = new ArrayList<>();
        final Map<String, RoleEntry> rolesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) { // Assuming CSV format: email,employeeId,password,role
                    UserEntry userEntry = new UserEntry();
                    userEntry.setCompany(companyEntry);
                    userEntry.setEmail(data[0]);
                    userEntry.setEmployeeId(data[1]);
                    userEntry.setPassword(data[2]);
                    userEntry.setRoles(List.of(getOrFetchRoles(rolesMap, data[3]))); // Roles start from index 3
                    userEntries.add(userEntry);
                }
            }
        }
        return userService.createAll(userEntries);
    }

    private RoleEntry getOrFetchRoles(final Map<String, RoleEntry> rolesMap, final String roleString) throws NotFoundException {
        if (!rolesMap.containsKey(roleString)) {
            final RoleEntry roleEntry = roleService.readByName(roleString);
            rolesMap.put(roleString, roleEntry);
        }
        return rolesMap.get(roleString);
    }


    public List<CompanyEmissionEntry> getCompanyEmissions(final String companyId) {
        return new ArrayList<>();
    }
}
