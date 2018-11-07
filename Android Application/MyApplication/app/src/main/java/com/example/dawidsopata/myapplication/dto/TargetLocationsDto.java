package com.example.dawidsopata.myapplication.dto;

import java.util.List;

public class TargetLocationsDto {

    private ClientDto clientData;
    private List<ServicePointDto> clientServicePoints;


    public ClientDto getClientData() {
        return clientData;
    }

    public void setClientData(ClientDto clientData) {
        this.clientData = clientData;
    }

    public List<ServicePointDto> getClientServicePoints() {
        return clientServicePoints;
    }

    public void setClientServicePoints(List<ServicePointDto> clientServicePoints) {
        this.clientServicePoints = clientServicePoints;
    }

    @Override
    public String toString() {
        return "TargetLocationsDto{" +
                "clientData=" + clientData +
                ", clientServicePoints=" + clientServicePoints +
                '}';
    }
}
