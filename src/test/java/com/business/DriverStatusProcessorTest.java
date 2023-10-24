package com.business;

import com.Dto.BusinessDTO;
import com.Repository.DriverAppDBService;
import com.Validation.DocVerValidationStrategy;
import com.Validation.DriverStatusValidationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class DriverStatusProcessorTest {
    @Mock
    DriverAppDBService driverDetailsDBService;
    @InjectMocks
    DriverStatusProcessor driverStatusProcessor;
    @Mock
    DriverStatusValidationStrategy driverStatusValidationStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcess() throws Exception {
        Mockito.doNothing().when(driverDetailsDBService).addEntry(any(), any(), anyBoolean());
        Mockito.when(driverStatusValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
            put("OK", new BusinessDTO() {
            });
        }});
        Map<String, BusinessDTO> result = driverStatusProcessor.process(new HashMap<String, String>() {{
            put("String", "String");
        }}, driverStatusValidationStrategy);
        Assertions.assertNotNull(result.get("OK"));
    }

    @Test
    void testDBException() {
        boolean flag = false;
        try {
            Mockito.doThrow(new SQLException()).when(driverDetailsDBService).addEntry(any(), any(), anyBoolean());
            Mockito.when(driverStatusValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
                put("OK", new BusinessDTO() {
                });
            }});
            Map<String, BusinessDTO> result = driverStatusProcessor.process(new HashMap<String, String>() {{
                put("String", "String");
            }}, driverStatusValidationStrategy);
            Assertions.assertNotNull(result.get("OK"));
        } catch (Exception e) {
            flag = true;
        }
        Assertions.assertTrue(flag);
    }
}
