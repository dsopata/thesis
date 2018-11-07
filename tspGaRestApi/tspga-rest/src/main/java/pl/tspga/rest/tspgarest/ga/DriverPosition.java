package pl.tspga.rest.tspgarest.ga;

import pl.tspga.rest.tspgarest.dto.LocationDto;

public  class DriverPosition {
    public static LocationDto driverCords;

    public static Boolean setDriverCords(LocationDto driverCords) {
        DriverPosition.driverCords = driverCords;
        return true;
    }
}
