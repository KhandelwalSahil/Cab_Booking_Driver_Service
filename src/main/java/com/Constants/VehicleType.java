package com.Constants;

public enum VehicleType {

    SEDAN(4, "Sedan"),
    SUV(6, "Suv"),
    HATCHBACK(3, "Hatchback");

    private int seats;
    private String type;

    VehicleType(int seats, String type) {
        this.seats = seats;
        this.type = type;
    }

    public static VehicleType getVehicleType(String vType) {
        for (VehicleType type : VehicleType.values()) {
            if (type.type.equals(vType)) {
                return type;
            }
        }
        return null;
    }
}
