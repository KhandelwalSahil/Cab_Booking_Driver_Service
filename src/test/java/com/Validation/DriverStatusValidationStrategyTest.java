package com.Validation;

import com.Dto.BusinessDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class DriverStatusValidationStrategyTest {
    DriverStatusValidationStrategy driverStatusValidationStrategy = new DriverStatusValidationStrategy();

    @Test
    void testValidate() {
        String phoneNo = "1234567890";
        String status = "online";
        String latitude = "Latitude";
        String longitude = "Longitude";
        Map<String, String> details = new HashMap<>();
        details.put("PhoneNo", phoneNo);
        details.put("Status", status);
        details.put(latitude, latitude);
        details.put(longitude, longitude);
        Map<String, BusinessDTO> result = driverStatusValidationStrategy.validate(details);
        Assertions.assertNotNull(result.get("OK"));
    }

    @Test
    void testInvalidData() {
        String phoneNo = "1234567890";
        String status = "invalidStatus";
        String latitude = "Latitude";
        String longitude = "Longitude";
        Map<String, String> details = new HashMap<>();
        details.put("PhoneNo", phoneNo);
        details.put("Status", status);
        details.put(latitude, latitude);
        details.put(longitude, longitude);
        Map<String, BusinessDTO> result = driverStatusValidationStrategy.validate(details);
        Assertions.assertNull(result.get("OK"));
    }
}
