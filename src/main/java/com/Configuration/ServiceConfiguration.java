package com.Configuration;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ServiceConfiguration implements EnvironmentAware {

    private static Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static String getValue(String key) {
        return environment.getProperty(key);
    }
}

