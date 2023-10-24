package com.Validation;

import com.Dto.BusinessDTO;
import com.Dto.DocVerDTO;
import com.Dto.SignUpDTO;
import com.util.CommonUtilities;
import com.util.GenerateCacheMap;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DocVerValidationStrategy implements InputDetailsValidationStrategy {

    static Logger logger = Logger.getLogger(DocVerValidationStrategy.class.getName());

    @Override
    public Map<String, BusinessDTO> validate(Map<String, String> details) {
        return validateDocs(details, details.get("savedOTP"));
    }

    private Map<String, BusinessDTO> validateDocs(Map<String, String> details, String savedOtp) {
        Map<String, BusinessDTO> statusToDTO = new HashMap<String, BusinessDTO>();
        String dlNo = details.get("DrivingLicense");
        String aadhaarNo = details.get("AadhaarNo");
        String driverID = details.get("DriverID");
        String userOTP = details.get("OTP");

        if (!isValidOTP(userOTP, savedOtp)) {
            statusToDTO.put("Invalid OTP", null);
            return statusToDTO;
        }if (!isValidDL(dlNo)) {
            statusToDTO.put("Invalid Driving License", null);
            return statusToDTO;
        }if (!isValidAadhaarNo(aadhaarNo)) {
            statusToDTO.put("Invalid Aadhaar Number", null);
            return statusToDTO;
        }
        DocVerDTO docVerDTO = DocVerDTO.builder().aadhaarCardNo(aadhaarNo)
                .dlNo(dlNo)
                .driverID(driverID).build();
        statusToDTO.put("OK", docVerDTO);
        logger.info("Document validation successful for userID: " + driverID);
        return statusToDTO;
    }

    private boolean isValidAadhaarNo(String aadhaarNo) {
        return aadhaarNo.matches("[0-9]+") && aadhaarNo.length() == 12;
    }

    private boolean isValidDL(String dlNo) {
        return !StringUtils.isEmpty(dlNo);
    }

    private boolean isValidOTP(String userOTP, String otp) {
        return userOTP.equals(otp);
    }
}
