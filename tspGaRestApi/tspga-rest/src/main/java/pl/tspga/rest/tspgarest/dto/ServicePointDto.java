package pl.tspga.rest.tspgarest.dto;

import pl.tspga.rest.tspgarest.entities.ServicePointsEntity;

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


    public ServicePointDto toDto(ServicePointsEntity servicePointsEntity) {

        LocationDto locationDto = new LocationDto();
        locationDto.setCordinateX(servicePointsEntity.getCordinateX());
        locationDto.setCordinateY(servicePointsEntity.getCordinateY());
        setLocationDto(locationDto);
        setName(servicePointsEntity.getName());
        setDetails(servicePointsEntity.getDetails());

        return this;
    }
}
