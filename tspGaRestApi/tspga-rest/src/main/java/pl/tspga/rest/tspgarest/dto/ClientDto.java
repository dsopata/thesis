package pl.tspga.rest.tspgarest.dto;


import pl.tspga.rest.tspgarest.entities.ClientEntity;

public class ClientDto {

    private String firstName;
    private String lastName;
    private LocationDto clientLocation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocationDto getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(LocationDto clientLocation) {
        this.clientLocation = clientLocation;
    }


    public ClientDto toDto(ClientEntity clientEntity) {
        setFirstName(clientEntity.getFirstName());
        setLastName(clientEntity.getLastName());
        LocationDto locationDto = new LocationDto();
        locationDto.setCordinateX(clientEntity.getCordinateX());
        locationDto.setCordinateY(clientEntity.getCordinateY());
        setClientLocation(locationDto);

        return this;
    }
}
