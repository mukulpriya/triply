package com.mukul.triply.common.baseclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

import static com.mukul.triply.constant.Constants.BASE_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntry {
    private String createdBy;

    private String updatedBy;

    @JsonFormat(pattern = BASE_DATE_TIME_FORMAT)
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = BASE_DATE_TIME_FORMAT)
    private ZonedDateTime updatedAt;
}