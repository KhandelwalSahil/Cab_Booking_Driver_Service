package com.Controller;

import com.Dto.BusinessDTO;
import com.Validation.DocVerValidationStrategy;
import com.business.BusinessProcessor;
import com.business.DocVerificationProcessor;
import com.util.CacheMap;
import com.util.GenerateCacheMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@RestController
public class DriverDocVerControllerImpl implements DriverDocVerController {

    @Autowired
    @Qualifier("docVerificationProcessor")
    private BusinessProcessor businessProcessor;

    static Logger logger = Logger.getLogger(DriverDocVerControllerImpl.class.getName());

    private CacheMap<String, Integer> cacheMap;

    public String getOTP(Map<String, String> details) {
        cacheMap = GenerateCacheMap.getCacheMap();
        String dlNo = details.get("DrivingLicense");
        logger.info("OTP generate request came for license no: " + dlNo);
        int otp = new Random().nextInt(900000) + 100000;
        cacheMap.put(dlNo, otp);
        return "OTP generated: " + otp + " valid for 1 min";
    }

    public String submitDocs(Map<String, String> details) {
        cacheMap = GenerateCacheMap.getCacheMap();
        String response = "";
        int savedOTP;
        try {
            savedOTP = cacheMap.getValue(details.get("DrivingLicense"));
        } catch (Exception e) {
            return "OTP Invalid or Expired";
        }
        try {
            details.put("savedOTP", String.valueOf(savedOTP));
            Map<String, BusinessDTO> evaluateDocs = businessProcessor.process(details, new DocVerValidationStrategy());
            if (evaluateDocs.get("OK") == null) {
                logger.info(evaluateDocs.keySet().toArray()[0].toString());
                return evaluateDocs.keySet().toArray()[0].toString();
            }
            response += "Docs successfully submitted and verified";
        } catch (Exception e) {
            logger.severe("Exception: " + e.getMessage());
            response += e.getMessage();
        }
        return response;
    }
}
