package com.business;

import com.Dto.BusinessDTO;
import com.Dto.DocVerDTO;
import com.Dto.TrackingDeviceDTO;
import com.Dto.UserCredentials;
import com.Repository.DriverAppDBService;
import com.Validation.InputDetailsValidationStrategy;
import com.util.CommonUtilities;
import com.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Service("trackingDeviceShipmentProcessor")
public class TrackingDeviceShipmentProcessor implements BusinessProcessor {

    @Autowired
    @Qualifier("driverDetailsDBImpl")
    private DriverAppDBService driverDetailsDBService;

    static Logger logger = Logger.getLogger(TrackingDeviceShipmentProcessor.class.getName());

    public Map<String, BusinessDTO> process(Map<String, String> details, InputDetailsValidationStrategy validationStrategy) throws Exception {
        Map<String, BusinessDTO> statusToDTO = validationStrategy.validate(details);

        if (statusToDTO.get("OK") != null && statusToDTO.get("OKUser") != null) {
            boolean userExist = driverDetailsDBService.getData("USER_CREDENTIALS_MATCH_AND_TRACKING_DEVICE_EXIST", statusToDTO.get("OKUser"), 1);
            if (userExist) {
                BusinessDTO trackingDeviceDTO = statusToDTO.get("OK");
                doDBTransactions(trackingDeviceDTO);
                logger.info("Tracking device dispatched");
                return statusToDTO;
            }
            else {
                logger.severe("Invalid credentials or Device already dispatched");
                throw new Exception("Invalid credentials or Device already dispatched");
            }
        }
        else {
            logger.severe(statusToDTO.keySet().toArray()[0].toString());
            throw new Exception(statusToDTO.keySet().toArray()[0].toString());
        }
    }

    private void doDBTransactions(BusinessDTO trackingDeviceDTO) throws Exception{
        Connection connection = null;
        Savepoint checkPointBefore = null;
        try {
            connection = DBUtil.getConnection();
            checkPointBefore = connection.setSavepoint();
            driverDetailsDBService.addEntry("CREATE_TRACKING_DEVICE_SHIPMENT", trackingDeviceDTO, false);
            driverDetailsDBService.addEntry("UPDATE_TRACKING_DEVICE_ID_DRIVER_TABLE", trackingDeviceDTO, false);
            driverDetailsDBService.addEntry("INSERT_DRIVER_STATUS", trackingDeviceDTO, false);
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
