package com.business;

import com.Constants.DriverStatus;
import com.Dto.BusinessDTO;
import com.Dto.DriverStatusDTO;
import com.Repository.DriverAppDBService;
import com.Validation.InputDetailsValidationStrategy;
import com.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service("driverStatusProcessor")
public class DriverStatusProcessor implements BusinessProcessor {

    @Autowired
    @Qualifier("driverDetailsDBImpl")
    private DriverAppDBService driverDetailsDBService;

    static Logger logger = Logger.getLogger(DriverStatusProcessor.class.getName());

    @Override
    public Map<String, BusinessDTO> process(Map<String, String> details, InputDetailsValidationStrategy validationStrategy) throws Exception {
        Map<String, BusinessDTO> statusToDTO = validationStrategy.validate(details);
        if (statusToDTO.get("OK") != null) {
            BusinessDTO statusDTO = statusToDTO.get("OK");
            driverDetailsDBService.addEntry("UPDATE_DRIVER_STATUS", statusDTO, true);
            logger.info("Updated driver status");
        }
        else {
            logger.severe("Error while changing status of Driver");
            throw new Exception(statusToDTO.keySet().toArray()[0].toString());
        }
        return statusToDTO;
    }
}
