package com.Validation;

import com.Constants.DriverStatus;
import com.Dto.BusinessDTO;
import com.Dto.DriverStatusDTO;
import com.Dto.SignUpDTO;
import com.util.CommonUtilities;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DriverStatusValidationStrategy implements InputDetailsValidationStrategy {

    static Logger logger = Logger.getLogger(DriverStatusValidationStrategy.class.getName());

    @Override
    public Map<String, BusinessDTO> validate(Map<String, String> details) {
        return validateDetails(details);
    }

    private Map<String, BusinessDTO> validateDetails(Map<String, String> details) {
        Map<String, BusinessDTO> statusToDTO = new HashMap<>();
        DriverStatus status = DriverStatus.getDriverStatus(details.get("Status"));
        String phoneNo = details.get("PhoneNo");
        String latitude = details.get("Latitude");
        String longitude = details.get("Longitude");

        if (!validateStatusAndLocation(status, latitude, longitude)) {
            statusToDTO.put("invalid location", null);
            return statusToDTO;
        }
        if (!validatePhoneNo(phoneNo)) {
            statusToDTO.put("invalid phoneNo", null);
            return statusToDTO;
        }
        DriverStatusDTO driverStatusDTO = DriverStatusDTO.builder().driverStatus(status)
                .phoneNo(phoneNo).latitude(latitude).longitude(longitude).build();
        statusToDTO.put("OK", driverStatusDTO);
        logger.info("Driver status request validated successfully for userID: " + phoneNo);
        return statusToDTO;
    }

    private boolean validatePhoneNo(String phoneNo) {
        return !StringUtils.isEmpty(phoneNo) && phoneNo.length() == 10;
    }

    private boolean validateStatusAndLocation(DriverStatus status, String latitude, String longitude) {
        return (DriverStatus.ONLINE.equals(status) && !StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude)) || DriverStatus.OFFLINE.equals(status);
    }
}
