package com.mukul.triply.runners;

import com.mukul.triply.features.company.CompanyEntity;
import com.mukul.triply.features.company.CompanyRepository;
import com.mukul.triply.features.role.RoleEntity;
import com.mukul.triply.features.role.RoleRepository;
import com.mukul.triply.features.user.UserEntity;
import com.mukul.triply.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DataIngestRunner implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        createRoles();
        createCompanyAndUser();
    }

    private void createRoles() {
        createRoleIfNotExists("SUPER_ADMIN");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createCompanyAndUser() {
        CompanyEntity company = companyRepository.findByName("Triply").orElse(null);
        if (company == null) {
            company = new CompanyEntity();
            company.setName("Triply");
            company.setDomain("triply.com");
            companyRepository.save(company);
        }

        UserEntity superAdmin = userRepository.findByEmail("superadmin@example.com").orElse(null);
        if (superAdmin == null) {
            superAdmin = new UserEntity();
            superAdmin.setCompany(company);
            superAdmin.setEmail("superadmin@example.com");
            superAdmin.setPassword("password");
            superAdmin.setRoles(Collections.singletonList(roleRepository.findByName("SUPER_ADMIN").orElse(null)));
            userRepository.save(superAdmin);
        }
    }
}