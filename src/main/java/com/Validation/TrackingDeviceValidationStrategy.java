package com.Validation;

import com.Dto.BusinessDTO;
import com.Dto.DocVerDTO;
import com.Dto.TrackingDeviceDTO;
import com.Dto.UserCredentials;
import com.util.CommonUtilities;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class TrackingDeviceValidationStrategy implements InputDetailsValidationStrategy {

    static Logger logger = Logger.getLogger(TrackingDeviceValidationStrategy.class.getName());

    @Override
    public Map<String, BusinessDTO> validate(Map<String, String> details) {
        return validateDetails(details);
    }

    private Map<String, BusinessDTO> validateDetails(Map<String, String> details) {
        Map<String, BusinessDTO> statusToDTO = new HashMap<>();
        String trackingId = UUID.randomUUID().toString();
        String deviceId = UUID.randomUUID().toString();
        String phoneNo = details.get("PhoneNo");
        String password = details.get("Password");
        String encryptPassword = CommonUtilities.encryptPassword(password);

        if (!isValidPhoneNo(phoneNo)) {
            statusToDTO.put("Invalid PhoneNo", null);
            return statusToDTO;
        }
        if (!isValidPassword(password)) {
            statusToDTO.put("Invalid password'", null);
            return statusToDTO;
        }

        UserCredentials userCredentials = UserCredentials.builder().phoneNo(phoneNo).password(encryptPassword).build();

        TrackingDeviceDTO trackingDeviceDTO = TrackingDeviceDTO.builder().deviceId(deviceId)
                .trackingID(trackingId).phoneNo(phoneNo).build();
        statusToDTO.put("OK", trackingDeviceDTO);
        statusToDTO.put("OKUser", userCredentials);
        logger.info("TrackingDevice request validated successfully for userID: " + phoneNo);
        return statusToDTO;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private boolean isValidPhoneNo(String phoneNo) {
        return phoneNo.length() == 10;
    }
}
