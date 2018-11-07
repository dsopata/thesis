package com.example.dawidsopata.myapplication.dto;

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

    @Override
    public String toString() {
        return "ClientDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", clientLocation=" + clientLocation +
                '}';
    }
}
