package com.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class DocVerDTO extends BusinessDTO {

    @NonNull
    private String driverID;

    @NonNull
    private String dlNo;

    @NonNull
    private String aadhaarCardNo;
}
