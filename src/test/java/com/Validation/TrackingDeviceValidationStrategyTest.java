package com.Validation;

import com.Dto.BusinessDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class TrackingDeviceValidationStrategyTest {
    TrackingDeviceValidationStrategy trackingDeviceValidationStrategy = new TrackingDeviceValidationStrategy();

    @Test
    void testValidate() {
        String phoneNo = "1234567890";
        Map<String, String> details = new HashMap<>();
        details.put("PhoneNo", phoneNo);
        details.put("Password", "password");
        Map<String, BusinessDTO> result = trackingDeviceValidationStrategy.validate(details);
        Assertions.assertNotNull(result.get("OK"));
        Assertions.assertNotNull(result.get("OKUser"));
    }

    @Test
    void testInvalidData() {
        String phoneNo = "123456789012";
        Map<String, String> details = new HashMap<>();
        details.put("PhoneNo", phoneNo);
        details.put("Password", "password");
        Map<String, BusinessDTO> result = trackingDeviceValidationStrategy.validate(details);
        Assertions.assertNull(result.get("OK"));
        Assertions.assertNull(result.get("OKUser"));
    }
}
