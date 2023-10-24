package com.Validation;

import com.Constants.VehicleType;
import com.Dto.BusinessDTO;
import com.Dto.CabDetailsDTO;
import com.Dto.SignUpDTO;
import com.util.CommonUtilities;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class SignUpValidationStrategy implements InputDetailsValidationStrategy {

    static Logger logger = Logger.getLogger(SignUpValidationStrategy.class.getName());

    @Override
    public Map<String, BusinessDTO> validate(Map<String, String> details) {
        return getValidSignUpDTO(details);
    }

    private Map<String, BusinessDTO> getValidSignUpDTO(Map<String, String> signUpFields) {

        Map<String, BusinessDTO> statusToDTO = new HashMap<String, BusinessDTO>();
        String name = signUpFields.get("Name");
        String emailId = signUpFields.get("EmailId");
        String phoneNo = signUpFields.get("PhoneNo");
        String pinCode = signUpFields.get("PinCode");
        String age = signUpFields.get("Age");
        String isValidDL = signUpFields.get("ValidDL");
        String address = signUpFields.get("Address");
        String password = signUpFields.get("Password");
        String driverID = UUID.randomUUID().toString();

        // vehicle details
        VehicleType vehicleType = VehicleType.getVehicleType(signUpFields.get("VehicleType"));
        String vehicleNo = signUpFields.get("VehicleNo");

        if (!isValidEmailId(emailId)) {
            statusToDTO.put("Invalid Email-Id", null);
            return statusToDTO;
        }
        if (!isValidPhoneNo(phoneNo)) {
            statusToDTO.put("Invalid PhoneNo", null);
            return statusToDTO;
        }
        if (!isValidAge(age)) {
            statusToDTO.put("Driver's Age should be more than 18", null);
            return statusToDTO;
        }
        if (!isValidPinCode(pinCode)) {
            statusToDTO.put("No location exist for given pinCode", null);
            return statusToDTO;
        }
        if (!isValidDL(isValidDL)) {
            statusToDTO.put("Driver must hold valid DL to be eligible", null);
            return statusToDTO;
        }
        if (!isValidAddress(address)) {
            statusToDTO.put("Driver's address is invalid'", null);
            return statusToDTO;
        }
        if (!isValidPassword(password)) {
            statusToDTO.put("invalid password", null);
            return statusToDTO;
        }

        if (!isValidVehicleDetails(vehicleNo, vehicleType)) {
            statusToDTO.put("Invalid Vehicle details'", null);
            return statusToDTO;
        }

        SignUpDTO signUpDTO = SignUpDTO.builder().name(name)
                .emailId(emailId)
                .age(Integer.valueOf(age))
                .driverID(driverID)
                .phoneNo(phoneNo)
                .pinCode(pinCode)
                .validDL(Boolean.valueOf(isValidDL))
                .address(address)
                .password(CommonUtilities.encryptPassword(password)).build();

        CabDetailsDTO cabDetailsDTO = CabDetailsDTO.builder().driverID(driverID).vehicleNo(vehicleNo).vehicleType(vehicleType).build();
        statusToDTO.put("OK", signUpDTO);
        statusToDTO.put("OKCab", cabDetailsDTO);
        logger.info("Driver sign up request validation done successfully for userID: " + phoneNo);
        return statusToDTO;
    }

    private boolean isValidVehicleDetails(String vehicleNo, VehicleType vehicleType) {
        return !(StringUtils.isEmpty(vehicleNo) || StringUtils.isEmpty(vehicleType));
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private boolean isValidAddress(String address) {
        return !StringUtils.isEmpty(address);
    }

    private boolean isValidDL(String isValidDL) {
        return Boolean.valueOf(isValidDL);
    }

    private boolean isValidPinCode(String pinCode) {
        return pinCode.length() == 6;
    }

    private boolean isValidAge(String age) {
        return Integer.valueOf(age).compareTo(18) != -1;
    }

    private boolean isValidPhoneNo(String phoneNo) {
        return phoneNo.length() == 10;
    }

    private boolean isValidEmailId(String emailId) {
        return EmailValidator.getInstance().isValid(emailId);
    }
}
