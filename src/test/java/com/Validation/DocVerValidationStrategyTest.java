package com.Validation;

import com.Dto.BusinessDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class DocVerValidationStrategyTest {
    DocVerValidationStrategy docVerValidationStrategy = new DocVerValidationStrategy();

    @Test
    void testValidate() {
        String dlNo = "DrivingLicense";
        String aadhaarNo = "123412341234";
        String driverID = "1234567890-abcd";
        String userOTP = "123456";
        Map<String, String> details = new HashMap<>();
        details.put("AadhaarNo", aadhaarNo);
        details.put("DriverID", driverID);
        details.put(dlNo, dlNo);
        details.put("OTP", userOTP);
        details.put("savedOTP", userOTP);
        Map<String, BusinessDTO> result = docVerValidationStrategy.validate(details);
        Assertions.assertNotNull(result.get("OK"));
    }

    @Test
    void testInvalidData() {
        String dlNo = "DrivingLicense";
        String aadhaarNo = "12345612341234";
        String phoneNo = "1234567890";
        String userOTP = "123456";
        Map<String, String> details = new HashMap<>();
        details.put("AadhaarNo", aadhaarNo);
        details.put("PhoneNo", phoneNo);
        details.put(dlNo, dlNo);
        details.put("OTP", userOTP);
        details.put("savedOTP", userOTP);
        Map<String, BusinessDTO> result = docVerValidationStrategy.validate(details);
        Assertions.assertNull(result.get("OK"));
    }
}
