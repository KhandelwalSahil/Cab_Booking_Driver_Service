package com.Validation;

import com.Dto.BusinessDTO;
import com.Dto.SignUpDTO;
import com.util.CommonUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class SignUpValidationStrategyTest {
    SignUpValidationStrategy signUpValidationStrategy = new SignUpValidationStrategy();

    @Test
    void testValidate() {
        String name = "Name";
        String emailId = "EmailId@gmail.com";
        String phoneNo = "1234567890";
        String pinCode = "123456";
        String age = "25";
        String isValidDL = "true";
        String address = "Address";
        String password = "Password";
        String vehicleNo = "ABC-1234";
        String vehicleType = "Suv";
        Map<String, String> details = new HashMap<>();
        details.put("Name", name);
        details.put("EmailId", emailId);
        details.put("PhoneNo", phoneNo);
        details.put("PinCode", pinCode);
        details.put("Age", age);
        details.put("ValidDL", isValidDL);
        details.put(address, address);
        details.put(password, password);
        details.put("VehicleNo", vehicleNo);
        details.put("VehicleType", vehicleType);
        Map<String, BusinessDTO> result = signUpValidationStrategy.validate(details);
        Assertions.assertNotNull(result.get("OK"));
    }

    @Test
    void testInvalidData() {
        String name = "Name";
        String emailId = "EmailId";
        String phoneNo = "1234567890";
        String pinCode = "123456";
        String age = "25";
        String isValidDL = "true";
        String address = "Address";
        String password = "Password";
        Map<String, String> details = new HashMap<>();
        details.put("Name", name);
        details.put("EmailId", emailId);
        details.put("PhoneNo", phoneNo);
        details.put("PinCode", pinCode);
        details.put("Age", age);
        details.put("ValidDL", isValidDL);
        details.put(address, address);
        details.put(password, password);
        Map<String, BusinessDTO> result = signUpValidationStrategy.validate(details);
        Assertions.assertNull(result.get("OK"));
    }
}
