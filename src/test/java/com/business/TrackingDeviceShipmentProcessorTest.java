package com.business;

import com.Dto.BusinessDTO;
import com.Repository.DriverAppDBService;
import com.Validation.SignUpValidationStrategy;
import com.Validation.TrackingDeviceValidationStrategy;
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

class TrackingDeviceShipmentProcessorTest {
    @Mock
    DriverAppDBService driverDetailsDBService;
    @InjectMocks
    TrackingDeviceShipmentProcessor trackingDeviceShipmentProcessor;
    @Mock
    TrackingDeviceValidationStrategy trackingDeviceValidationStrategy;
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
            Mockito.when(trackingDeviceValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
                put("OK", new BusinessDTO() {
                });
                put("OKUser", new BusinessDTO() {
                });
            }});
            Map<String, BusinessDTO> result = trackingDeviceShipmentProcessor.process(new HashMap<String, String>() {{
                put("String", "String");
            }}, trackingDeviceValidationStrategy);
            Assertions.assertNotNull(result.get("OK"));
            Assertions.assertNotNull(result.get("OKUser"));
        }
    }

    @Test
    void testUserDoesNotExist() {
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            boolean flag = false;
            try {
                Mockito.doThrow(new SQLException()).when(driverDetailsDBService).addEntry(any(), any(), anyBoolean());
                Mockito.when(trackingDeviceValidationStrategy.validate(anyMap())).thenReturn(new HashMap<String, BusinessDTO>() {{
                    put("OK", new BusinessDTO() {
                    });
                    put("OKUser", new BusinessDTO() {
                    });
                }});
                Map<String, BusinessDTO> result = trackingDeviceShipmentProcessor.process(new HashMap<String, String>() {{
                    put("String", "String");
                }}, trackingDeviceValidationStrategy);
                Assertions.assertNotNull(result.get("OK"));
            } catch (Exception e) {
                Assertions.assertEquals("Invalid credentials or Device already dispatched", e.getMessage());
                flag = true;
            }
            Assertions.assertTrue(flag);
        }
    }
}