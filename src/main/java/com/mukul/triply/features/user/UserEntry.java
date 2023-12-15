package com.mukul.triply.features.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mukul.triply.common.baseclasses.BaseEntry;
import com.mukul.triply.features.company.CompanyEntry;
import com.mukul.triply.features.role.RoleEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntry extends BaseEntry {
    private String id;

    private CompanyEntry company;

    private String email;

    @JsonIgnore
    private String password;

    private String employeeId;

    private Collection<RoleEntry> roles;
}
