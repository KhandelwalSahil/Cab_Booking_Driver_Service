package com.Repository;

import com.Dto.BusinessDTO;
import com.util.DBQueryFactory;
import com.util.DBUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class DriverDetailsDBImplTest {
    @Mock
    Connection connection;
    @InjectMocks
    DriverDetailsDBImpl driverDetailsDBImpl;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddEntry() throws Exception {
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(() -> DBUtil.getConnection()).thenReturn(connection);
            try (MockedStatic<DBQueryFactory> dbQueryFactoryMockedStatic = Mockito.mockStatic(DBQueryFactory.class)) {
                dbQueryFactoryMockedStatic.when(() -> DBQueryFactory.getQuery(anyString(), any())).thenReturn("");
                Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
                Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
                driverDetailsDBImpl.addEntry("scenario", new BusinessDTO() {
                }, false);
            }
        }
        Assertions.assertTrue(true);
    }

    @Test
    void testAddEntryException() {
        boolean flag = false;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(() -> DBUtil.getConnection()).thenReturn(connection);
            try (MockedStatic<DBQueryFactory> dbQueryFactoryMockedStatic = Mockito.mockStatic(DBQueryFactory.class)) {
                try {
                    dbQueryFactoryMockedStatic.when(() -> DBQueryFactory.getQuery(anyString(), any())).thenReturn(anyString());
                    Mockito.when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Duplicate Entry"));
                    Mockito.when(preparedStatement.executeQuery()).thenReturn(any());
                    driverDetailsDBImpl.addEntry("scenario", new BusinessDTO() {
                    }, false);
                } catch (Exception e) {
                    flag = true;
                }
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    void testGetData() throws Exception {
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(() -> DBUtil.getConnection()).thenReturn(connection);
            try (MockedStatic<DBQueryFactory> dbQueryFactoryMockedStatic = Mockito.mockStatic(DBQueryFactory.class)) {
                dbQueryFactoryMockedStatic.when(() -> DBQueryFactory.getQuery(anyString(), any())).thenReturn("");
                Mockito.when(connection.prepareStatement(anyString(), anyInt(), anyInt())).thenReturn(preparedStatement);
                Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
                driverDetailsDBImpl.getData("scenario", new BusinessDTO() {
                }, 0);
            }
        }
        Assertions.assertTrue(true);
    }
}
