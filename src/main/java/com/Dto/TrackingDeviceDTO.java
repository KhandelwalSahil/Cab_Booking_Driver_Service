package com.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@Builder
public class TrackingDeviceDTO extends BusinessDTO {

    @NonNull
    private String trackingID;

    @NonNull
    private String phoneNo;

    @NonNull
    private String deviceId;
}
