package com.business;

import com.Dto.BusinessDTO;
import com.Repository.DriverAppDBService;
import com.Validation.DocVerValidationStrategy;
import com.Validation.SignUpValidationStrategy;
import com.util.DBUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class SignUpProcessorTest {
    @Mock
    DriverAppDBService driverDetailsDBService;
    @InjectMocks
    SignUpProcessor signUpProcessor;
    @Mock
    SignUpValidationStrategy signUpValidationStrategy;
    @Mock
    Connection connection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcess() throws Exception {
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(() -> DBUtil.getConnection()).thenReturn(connection);
            Mockito.doNothing().when(driverDetailsDBService).addEntry(any(), any(), anyBoolean());
            Mockito.when(driverDetailsDBService.getData(anyString(), any(), anyInt())).thenReturn(true);
            Mockito.when(signUpValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
                put("OK", new BusinessDTO() {
                });
                put("OKCab", new BusinessDTO() {
                });
            }});
            Map<String, BusinessDTO> result = signUpProcessor.process(new HashMap<String, String>() {{
                put("String", "String");
            }}, signUpValidationStrategy);
            Assertions.assertNotNull(result.get("OK"));
            Assertions.assertNotNull(result.get("OKCab"));
        }
    }

    @Test
    void testDBException() {
        boolean flag = false;
        try {
            Mockito.doThrow(new SQLException()).when(driverDetailsDBService).addEntry(any(), any(), anyBoolean());
            Mockito.when(signUpValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
                put("OK", new BusinessDTO() {
                });
            }});
            Map<String, BusinessDTO> result = signUpProcessor.process(new HashMap<String, String>() {{
                put("String", "String");
            }}, signUpValidationStrategy);
            Assertions.assertNotNull(result.get("OK"));
        } catch (Exception e) {
            flag = true;
        }
        Assertions.assertTrue(flag);
    }
}
