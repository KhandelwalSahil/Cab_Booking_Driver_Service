package com.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UserCredentials extends BusinessDTO {

    @NonNull
    private String phoneNo;

    @NonNull
    private String password;
}
