package com.Controller;

import com.Dto.BusinessDTO;
import com.Dto.TrackingDeviceDTO;
import com.Validation.TrackingDeviceValidationStrategy;
import com.business.BusinessProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class TrackingDeviceControllerImpl implements TrackingDeviceController {

    @Autowired
    @Qualifier("trackingDeviceShipmentProcessor")
    private BusinessProcessor trackingDeviceBusinessProcessor;

    static Logger logger = Logger.getLogger(TrackingDeviceControllerImpl.class.getName());

    @Override
    public String dispatchTrackingDevice(Map<String, String> credentials) {
        String trackingId = null;
        try {
            trackingId = initiateTrackingDevice(credentials);
        } catch (Exception e) {
            return e.getMessage();
        }
        if (StringUtils.isEmpty(trackingId)) {
            logger.severe("Invalid credentials");
            return "Please enter valid credentials";
        }
        logger.info("Tracking device dispatched with trackingID: " + trackingId);
        return "Tracking device has been dispatched with trackingId: " + trackingId;
    }

    private String initiateTrackingDevice(Map<String, String> details) throws Exception{
        try {
            Map<String, BusinessDTO> statusToDTO = trackingDeviceBusinessProcessor.process(details, new TrackingDeviceValidationStrategy());
            return ((TrackingDeviceDTO) statusToDTO.get("OK")).getTrackingID();
        } catch (Exception e) {
            logger.severe("Error: Error while creating shipment for tracking device");
            throw e;
        }
    }
}
