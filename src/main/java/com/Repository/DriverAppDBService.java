package com.Repository;

import com.Dto.BusinessDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverAppDBService {

    public void addEntry(String scenario, BusinessDTO businessDTO, boolean commit) throws Exception;
    public boolean getData(String scenario, BusinessDTO businessDTO, int fetchSize) throws Exception;
}
