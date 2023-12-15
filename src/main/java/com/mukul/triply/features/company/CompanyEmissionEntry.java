package com.mukul.triply.features.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.time.Month;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmissionEntry {
    private Year year;

    private Month month;

    private Integer week;

    private Double totalEmission;
}
