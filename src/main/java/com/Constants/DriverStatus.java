package com.Constants;

public enum DriverStatus {

    ONLINE("online"),
    OFFLINE("offline");

    private String status;

    DriverStatus(String status) {
        this.status = status;
    }

    public static DriverStatus getDriverStatus(String status) {
        for (DriverStatus type : DriverStatus.values()) {
            if (type.status.equals(status)) {
                return type;
            }
        }
        return null;
    }
}
