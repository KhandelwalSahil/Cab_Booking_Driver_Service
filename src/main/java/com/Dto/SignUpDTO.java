package com.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@Builder
public class SignUpDTO extends BusinessDTO {

    @NonNull
    private String name;

    @NonNull
    private String driverID;

    @NonNull
    private String phoneNo;

    @NonNull
    private String emailId;

    @NonNull
    private String pinCode;

    @NonNull
    private Integer age;

    @NonNull
    private Boolean validDL;

    @NonNull
    private String address;

    @NonNull
    private String password;

    private String trackingDeviceId;

    private Boolean areDocsVerified;


}
