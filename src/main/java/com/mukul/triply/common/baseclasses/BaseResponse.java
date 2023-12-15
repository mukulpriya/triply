package com.mukul.triply.common.baseclasses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponse<T extends BaseEntry> {

    public T entry;

    public List<T> entries;

    public BaseResponse(@NonNull final T entry) {
        this.entry = entry;
    }

    public BaseResponse(@NonNull final List<T> entries) {
        this.entries = entries;
    }
}