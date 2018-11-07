package com.example.dawidsopata.myapplication.dto;

public class ServicePointDto {

    private LocationDto locationDto;
    private String name;
    private String details;

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ServicePointDto{" +
                "locationDto=" + locationDto +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
