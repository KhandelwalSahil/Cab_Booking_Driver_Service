package com.util;

import com.Dto.*;

public class DBQueryFactory {

    public static String getQuery(String scenario, BusinessDTO businessDTO) {

        switch (scenario) {
            case "DRIVER_TABLE_INSERTION":
                if (businessDTO instanceof SignUpDTO) {
                    SignUpDTO signUpDTO = (SignUpDTO) businessDTO;
                    return "INSERT INTO driver_details VALUES('"+signUpDTO.getDriverID()+"','"+signUpDTO.getName()+"','"+signUpDTO.getEmailId()+"', "+signUpDTO.getAge()+", '"+signUpDTO.getPhoneNo()+"', '"+signUpDTO.getPinCode()+"', '"+signUpDTO.getAddress()+"', "+signUpDTO.getAreDocsVerified()+", '"+signUpDTO.getPassword()+"', NULL);";
                }
                break;
            case "CAB_TABLE_INSERTION":
                if (businessDTO instanceof CabDetailsDTO) {
                    CabDetailsDTO cabDetailsDTO = (CabDetailsDTO) businessDTO;
                    return "INSERT INTO cab_table VALUES('"+cabDetailsDTO.getDriverID()+"','"+cabDetailsDTO.getVehicleNo()+"','"+cabDetailsDTO.getVehicleType()+"');";
                }
                break;
            case "DOC_TABLE_INSERTION":
                if (businessDTO instanceof DocVerDTO) {
                    DocVerDTO docVerDTO = (DocVerDTO) businessDTO;
                    return "INSERT INTO driver_docs VALUES('"+docVerDTO.getDriverID()+"','"+docVerDTO.getAadhaarCardNo()+"', '"+docVerDTO.getDlNo()+"');";
                }
                break;
            case "DRIVER_TABLE_DOC_VERIFY":
                if (businessDTO instanceof DocVerDTO) {
                    DocVerDTO docVerDTO = (DocVerDTO) businessDTO;
                    return "UPDATE driver_details SET areDocsVerified=true where driverID='"+docVerDTO.getDriverID()+"';";
                }
                break;
            case "ARE_DOCS_VERIFIED":
                if (businessDTO instanceof DocVerDTO) {
                    DocVerDTO docVerDTO = (DocVerDTO) businessDTO;
                    return "SELECT areDocsVerified FROM driver_details where driverID='"+docVerDTO.getDriverID()+"' AND areDocsVerified=1;";
                }
                break;
            case "CREATE_TRACKING_DEVICE_SHIPMENT":
                if (businessDTO instanceof TrackingDeviceDTO) {
                    TrackingDeviceDTO trackingDeviceDTO = (TrackingDeviceDTO) businessDTO;
                    return "INSERT INTO tracking_device_details VALUES('"+trackingDeviceDTO.getPhoneNo()+"','"+trackingDeviceDTO.getDeviceId()+"', '"+trackingDeviceDTO.getTrackingID()+"');";
                }
                break;
            case "USER_CREDENTIALS_MATCH_AND_TRACKING_DEVICE_EXIST":
                if (businessDTO instanceof UserCredentials) {
                    UserCredentials userCredentials = (UserCredentials) businessDTO;
                    return "SELECT * FROM driver_details where phoneNo='"+userCredentials.getPhoneNo()+"' AND password='"+userCredentials.getPassword()+"' AND areDocsVerified=1 AND trackingDeviceID IS NULL;";
                }
                break;
            case "UPDATE_TRACKING_DEVICE_ID_DRIVER_TABLE":
                if (businessDTO instanceof TrackingDeviceDTO) {
                    TrackingDeviceDTO trackingDeviceDTO = (TrackingDeviceDTO) businessDTO;
                    return "UPDATE driver_details SET trackingDeviceId='"+trackingDeviceDTO.getTrackingID()+"' where phoneNo='"+trackingDeviceDTO.getPhoneNo()+"';";
                }
                break;
            case "INSERT_DRIVER_STATUS":
                if (businessDTO instanceof TrackingDeviceDTO) {
                    TrackingDeviceDTO trackingDeviceDTO = (TrackingDeviceDTO) businessDTO;
                    return "INSERT INTO driver_status VALUES('offline','"+trackingDeviceDTO.getPhoneNo()+"', 'NULL', 'NULL');";
                }
                break;
            case "UPDATE_DRIVER_STATUS":
                if (businessDTO instanceof DriverStatusDTO) {
                    DriverStatusDTO driverStatusDTO = (DriverStatusDTO) businessDTO;
                    return "UPDATE driver_status SET status='"+driverStatusDTO.getDriverStatus().toString()+"', latitude='"+driverStatusDTO.getLatitude()+"', longitude='"+driverStatusDTO.getLongitude()+"' where phoneNo='"+driverStatusDTO.getPhoneNo()+"';";
                }
                break;
        }
        return null;
    }
}
