package pl.tspga.rest.tspgarest.dto;

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
}
