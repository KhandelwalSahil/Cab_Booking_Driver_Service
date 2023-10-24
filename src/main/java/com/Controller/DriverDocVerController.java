package com.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface DriverDocVerController {

    /* Building concurrent in-memory hash, assuming only one pod is running,
        in case of load balancer distributing to different system,
        we can use in-memory caching system such as REDIS

     */

    @PostMapping("/getOtp")
    public String getOTP(@RequestBody Map<String, String> details);

    @PostMapping("/submitDocs")
    public String submitDocs(@RequestBody Map<String, String> details);

}
