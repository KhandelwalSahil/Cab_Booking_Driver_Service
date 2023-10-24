package com.util;

import com.Configuration.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@Component
public class GenerateCacheMap {

    private static volatile CacheMap<String, Integer> cacheMap;

    static Logger logger = Logger.getLogger(GenerateCacheMap.class.getName());

    public static CacheMap<String, Integer> getCacheMap() {
        if (cacheMap == null) {
            synchronized (GenerateCacheMap.class) {
                if (cacheMap == null) {
                    cacheMap = new CacheMap<String, Integer>(60000);
                    logger.info("CacheMap instantiated");
                }
            }
        }
        return cacheMap;
    }
}
