package com.business;

import com.Dto.BusinessDTO;
import com.Dto.SignUpDTO;
import com.Repository.DriverAppDBService;
import com.Validation.InputDetailsValidationStrategy;
import com.util.CommonUtilities;
import com.util.DBUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service("signUpProcessor")
public class SignUpProcessor implements BusinessProcessor {

    @Autowired
    @Qualifier("driverDetailsDBImpl")
    private DriverAppDBService driverDetailsDBService;

    static Logger logger = Logger.getLogger(SignUpProcessor.class.getName());

    public Map<String, BusinessDTO> process(Map<String, String> details, InputDetailsValidationStrategy inputDetailsValidationStrategy) throws Exception {
        Map<String, BusinessDTO> validationResponse = inputDetailsValidationStrategy.validate(details);
        if (validationResponse.get("OK") != null && validationResponse.get("OKCab") != null) {
            BusinessDTO signUpDTO = validationResponse.get("OK");
            BusinessDTO cabDetailsDTO = validationResponse.get("OKCab");
            doDBTransactions(signUpDTO, cabDetailsDTO);
            if (signUpDTO != null) {
                logger.info("Saved driver details in DB");
            } else {
                logger.severe("Error while creating SignUp Object");
                throw new Exception("Error while creating SignUp Object");
            }
        }
        else {
            logger.severe(validationResponse.keySet().toArray()[0].toString());
            throw new Exception(validationResponse.keySet().toArray()[0].toString());
        }
        return validationResponse;
    }

    private void doDBTransactions(BusinessDTO signUpDTO, BusinessDTO cabDetailsDTO) throws Exception{
        Connection connection = null;
        Savepoint checkPointBefore = null;
        try {
            connection = DBUtil.getConnection();
            checkPointBefore = connection.setSavepoint();
            driverDetailsDBService.addEntry("DRIVER_TABLE_INSERTION", signUpDTO, false);
            driverDetailsDBService.addEntry("CAB_TABLE_INSERTION", cabDetailsDTO, false);
            connection.commit();
        } catch (Exception e) {
            if (checkPointBefore != null) {
                connection.rollback(checkPointBefore);
                connection.commit();
            }
            logger.severe("Unable to connect to DB");
            throw new Exception("Unable to connect to DB");
        }
    }
}
