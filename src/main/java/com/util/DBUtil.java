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
public class DBUtil {

    private static volatile Connection connection;

    static Logger logger = Logger.getLogger(DBUtil.class.getName());

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            synchronized (DBUtil.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(ServiceConfiguration.getValue("spring.datasource.url"),
                            ServiceConfiguration.getValue("spring.datasource.username"), ServiceConfiguration.getValue("spring.datasource.password"));
                    logger.info("DB Connection Established");
                    connection.setAutoCommit(false);
                }
            }
        }
        return connection;
    }
}
