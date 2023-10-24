package com.Controller;

import com.Dto.BusinessDTO;
import com.Dto.SignUpDTO;
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

class DriverRegisterControllerImplTest {
    @Mock
    BusinessProcessor businessProcessor;
    @InjectMocks
    DriverRegisterControllerImpl driverRegisterControllerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser() throws Exception {
        when(businessProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenReturn(new HashMap<String, BusinessDTO>() {{
            put("OK", new BusinessDTO() {
            });
        }});

        String result = driverRegisterControllerImpl.registerUser(new HashMap<String, String>() {{
            put("Name", "User");
        }});
        Assertions.assertEquals("Sign up successful, Welcome User!", result);
    }

    @Test
    void testAlreadyRegisteredUser() throws Exception {
        when(businessProcessor.process(ArgumentMatchers.<String, String>anyMap(), any(InputDetailsValidationStrategy.class))).thenThrow(new Exception("User already registered"));
        String result = driverRegisterControllerImpl.registerUser(new HashMap<String, String>() {{
            put("Name", "User");
        }});
        Assertions.assertEquals("User already registered", result);
    }
}
