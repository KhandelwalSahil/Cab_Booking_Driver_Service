package com.Controller;

import com.Dto.BusinessDTO;
import com.Dto.SignUpDTO;
import com.Validation.SignUpValidationStrategy;
import com.business.BusinessProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class DriverRegisterControllerImpl implements DriverRegisterController {

    @Autowired
    @Qualifier("signUpProcessor")
    private BusinessProcessor businessProcessor;

    static Logger logger = Logger.getLogger(DriverRegisterControllerImpl.class.getName());

    public String registerUser(@RequestBody Map<String, String> signUpFields) {

        String response = "";
        try {
            Map<String, BusinessDTO> statusToDTO = businessProcessor.process(signUpFields, new SignUpValidationStrategy());
            if (statusToDTO.get("OK") == null) {
                String error = statusToDTO.keySet().toArray()[0].toString();
                logger.severe(error);
                return error;
            }
            String driverName = signUpFields.get("Name");
            response += "Sign up successful, Welcome " + driverName + "!";
            logger.info("Sign up done, username: " + driverName);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            response += e.getMessage();
        }
        return response;
    }
}
