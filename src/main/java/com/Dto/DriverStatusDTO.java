package com.Dto;

import com.Constants.DriverStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class DriverStatusDTO extends BusinessDTO {

    @NonNull
    private DriverStatus driverStatus;

    @NonNull
    private String phoneNo;

    private String latitude;

    private String longitude;
}
