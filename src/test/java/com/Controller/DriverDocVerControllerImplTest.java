package com.Controller;

import com.Dto.BusinessDTO;
import com.Validation.InputDetailsValidationStrategy;
import com.business.BusinessProcessor;
import com.util.CacheMap;
import com.util.DBQueryFactory;
import com.util.DBUtil;
import com.util.GenerateCacheMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class DriverDocVerControllerImplTest {
    @Mock
    BusinessProcessor businessProcessor;
    @Mock
    CacheMap<String, Integer> cacheMap;
    @InjectMocks
    DriverDocVerControllerImpl driverDocVerControllerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
    void testGetOTP() {
        when(cacheMap.put(anyString(), anyInt())).thenReturn(ArgumentMatchers.<Integer>any());

        String result = driverDocVerControllerImpl.getOTP(new HashMap<String, String>() {{
            put("DrivingLicense", "String");
        }});
        Assertions.assertTrue(result.contains("OTP generated:"));
    }

//    @Test
    void testSubmitDocs() throws Exception {
        // TODO: Controller test case
        when(businessProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenReturn(new HashMap<String, BusinessDTO>() {{
            put("OK", new BusinessDTO() {
            });
        }});

        try (MockedStatic<GenerateCacheMap> generateCacheMapMockedStatic = Mockito.mockStatic(GenerateCacheMap.class)) {
            generateCacheMapMockedStatic.when(GenerateCacheMap::getCacheMap).thenReturn(cacheMap);
            when(cacheMap.getValue(anyString())).thenReturn(123456);

            String result = driverDocVerControllerImpl.submitDocs(new HashMap<String, String>() {{
                put("DrivingLicense", "1234567890");
            }});
            Assertions.assertEquals("Docs successfully submitted and verified", result);
        }
    }
}
