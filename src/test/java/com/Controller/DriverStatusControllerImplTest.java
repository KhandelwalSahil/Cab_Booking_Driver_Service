package com.Controller;

import com.Dto.BusinessDTO;
import com.Validation.InputDetailsValidationStrategy;
import com.business.BusinessProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class DriverStatusControllerImplTest {
    @Mock
    BusinessProcessor driverStatusProcessor;
    @InjectMocks
    DriverStatusControllerImpl driverStatusControllerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDriverStatus() throws Exception {
        when(driverStatusProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenReturn(new HashMap<String, BusinessDTO>() {{
            put("OK", new BusinessDTO() {
            });
        }});

        String result = driverStatusControllerImpl.driverStatus(new HashMap<String, String>() {{
            put("String", "String");
        }});
        Assertions.assertEquals("Driver's status changed", result);
    }
}
