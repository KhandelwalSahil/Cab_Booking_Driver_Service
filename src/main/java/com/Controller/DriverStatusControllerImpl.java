package com.Controller;

import com.Validation.DriverStatusValidationStrategy;
import com.business.BusinessProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class DriverStatusControllerImpl implements DriverStatusController {

    @Autowired
    @Qualifier("driverStatusProcessor")
    private BusinessProcessor driverStatusProcessor;

    static Logger logger = Logger.getLogger(DriverStatusControllerImpl.class.getName());

    @Override
    public String driverStatus(Map<String, String> status) {
        try {
            driverStatusProcessor.process(status, new DriverStatusValidationStrategy());
            logger.info("Driver's status changed successfully");
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return e.getMessage();
        }
        return "Driver's status changed";
    }
}
