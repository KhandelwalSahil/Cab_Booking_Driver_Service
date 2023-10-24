package com.Validation;

import com.Dto.BusinessDTO;

import java.util.Map;

public interface InputDetailsValidationStrategy {

    public Map<String, BusinessDTO> validate(Map<String, String> details);
}
