package com.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface DriverRegisterController {

    @PostMapping("/register")
    public String registerUser (@RequestBody Map<String, String> signUpFields);
}
