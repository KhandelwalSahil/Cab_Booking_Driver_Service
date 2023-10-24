package com.Repository;

import com.Controller.TrackingDeviceControllerImpl;
import com.Dto.BusinessDTO;
import com.util.DBQueryFactory;
import com.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@Repository("driverDetailsDBImpl")
public class DriverDetailsDBImpl implements DriverAppDBService {

    private Connection connection;

    static Logger logger = Logger.getLogger(DriverDetailsDBImpl.class.getName());

    public void addEntry(String scenario, BusinessDTO businessDTO, boolean commit) throws Exception {
        try {
            connection = DBUtil.getConnection();
            if (businessDTO != null) {
                String query = DBQueryFactory.getQuery(scenario, businessDTO);
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.execute();
                    logger.info(scenario + ": Query Executed");
                    if (commit) {
                        connection.commit();
                    }
                } catch (Exception e) {
                    logger.severe("Error while performing: " + scenario);
                    throw new Exception("Error while performing: " + scenario);
                }
            } else {
                logger.severe("Scenario: " + scenario + " Invalid data to save");
            }
        } catch (SQLException sqlException) {
            logger.severe("Error while connecting to DB");
            throw new Exception("Error while performing: " + scenario);
        }
    }

    @Override
    public boolean getData(String scenario, BusinessDTO businessDTO, int fetchSize) throws Exception {
        try {
            connection = DBUtil.getConnection();
            String query = DBQueryFactory.getQuery(scenario, businessDTO);
            try {
                PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet resultSet = statement.executeQuery();
                int noOfResults = getResultSize(resultSet);
                logger.info(scenario + ": Query Executed");
                return noOfResults == fetchSize;
            } catch (Exception e) {
                logger.severe("Error while performing: " + scenario);
                throw new Exception("Error while performing: " + scenario);
            }
        } catch (SQLException sqlException) {
            logger.severe("Error while connecting to DB");
        }
        return false;
    }

    private int getResultSize(ResultSet resultSet) {
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException sqlE) {
            logger.severe("No results fetched");
            return -1;
        }
    }
}
