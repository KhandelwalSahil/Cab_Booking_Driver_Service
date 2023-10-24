package com.Controller;

import com.Dto.BusinessDTO;
import com.Dto.TrackingDeviceDTO;
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

class TrackingDeviceControllerImplTest {
    @Mock
    BusinessProcessor trackingDeviceBusinessProcessor;
    @InjectMocks
    TrackingDeviceControllerImpl trackingDeviceControllerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDispatchTrackingDevice() throws Exception {
        when(trackingDeviceBusinessProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenReturn(new HashMap<String, BusinessDTO>() {{
            put("OK", new TrackingDeviceDTO("trackingId", "phoneNo", "deviceId"));
        }});

        String result = trackingDeviceControllerImpl.dispatchTrackingDevice(new HashMap<String, String>() {{
            put("String", "String");
        }});
        Assertions.assertEquals("Tracking device has been dispatched with trackingId: trackingId", result);
    }

    @Test
    void testAlreadyDispatchedTrackingDevice() throws Exception {
        when(trackingDeviceBusinessProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenThrow(new Exception("Already dispatched"));

        String result = trackingDeviceControllerImpl.dispatchTrackingDevice(new HashMap<String, String>() {{
            put("String", "String");
        }});
        Assertions.assertEquals("Already dispatched", result);
    }
}
