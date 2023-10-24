package com.Dto;

import com.Constants.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CabDetailsDTO extends BusinessDTO {

    @NonNull
    private String driverID;

    @NonNull
    private String vehicleNo;

    @NonNull
    private VehicleType vehicleType;
}
