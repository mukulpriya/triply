package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntry extends BaseEntry {
    private String id;

    private String name;

    private String domain;
}
