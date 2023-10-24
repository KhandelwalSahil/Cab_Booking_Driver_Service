package com.business;

import com.Dto.BusinessDTO;
import com.Validation.InputDetailsValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BusinessProcessor {

    /*
        for all controllers, assuming correct fields be sent (by applying mandatory filter on UI)
     */

    public Map<String, BusinessDTO> process(Map<String, String> details, InputDetailsValidationStrategy validationStrategy) throws Exception;
}
