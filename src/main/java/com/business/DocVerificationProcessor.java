package com.business;

import com.Dto.BusinessDTO;
import com.Dto.DocVerDTO;
import com.Repository.DriverAppDBService;
import com.Validation.InputDetailsValidationStrategy;
import com.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service("docVerificationProcessor")
public class DocVerificationProcessor implements BusinessProcessor {

    @Autowired
    @Qualifier("driverDetailsDBImpl")
    private DriverAppDBService driverDetailsDBService;

    static Logger logger = Logger.getLogger(DocVerificationProcessor.class.getName());

    public Map<String, BusinessDTO> process(Map<String, String> details, InputDetailsValidationStrategy validationStrategy) throws Exception {
        Map<String, BusinessDTO> validationResponse = validationStrategy.validate(details);
        if (validationResponse.get("OK") != null) {
            BusinessDTO docVerDTO = validationResponse.get("OK");
            if (docVerDTO != null) {
                // if already done, return
                if (isAlreadyVerified(docVerDTO)) {
                    validationResponse.clear();
                    validationResponse.put("Documents already verified", null);
                    return validationResponse;
                }
                if (!doesUserExist(docVerDTO)) {
                    validationResponse.clear();
                    validationResponse.put("User does not exist", null);
                    return validationResponse;
                }
                doDBTransactions(docVerDTO);
                logger.info("Saved doc verify details in DB");
            } else {
                logger.severe("Error while validating documents");
                throw new Exception("Error Error while validating documents");
            }
        }
        else {
            throw new Exception(validationResponse.keySet().toArray()[0].toString());
        }
        return validationResponse;
    }

    private void doDBTransactions(BusinessDTO docVerDTO) throws Exception{
        Connection connection = null;
        Savepoint checkPointBefore = null;
        try {
            connection = DBUtil.getConnection();
            checkPointBefore = connection.setSavepoint();
            driverDetailsDBService.addEntry("DRIVER_TABLE_DOC_VERIFY", docVerDTO, false);
            driverDetailsDBService.addEntry("DOC_TABLE_INSERTION", docVerDTO, false);
            connection.commit();
        } catch (Exception e) {
            if (checkPointBefore != null) {
                connection.rollback(checkPointBefore);
                connection.commit();
            }
            logger.severe("Error: Unable to connect to DB");
            throw new Exception("Unable to connect to DB");
        }
    }

    private boolean isAlreadyVerified(BusinessDTO docVerDTO) throws Exception {
        try {
            boolean areDocsVerified = driverDetailsDBService.getData("ARE_DOCS_VERIFIED", docVerDTO, 1);
            return areDocsVerified;
        } catch (Exception e) {
            throw new Exception("Something went wrong, please try again later");
        }
    }

    private boolean doesUserExist(BusinessDTO docVerDTO) throws Exception {
        try {
            boolean doesUserExist = driverDetailsDBService.getData("USER_EXIST", docVerDTO, 1);
            return doesUserExist;
        } catch (Exception e) {
            throw new Exception("Something went wrong, please try again later");
        }
    }
}
