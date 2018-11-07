package pl.tspga.rest.tspgarest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tspga.rest.tspgarest.AsyncComponent;
import pl.tspga.rest.tspgarest.dto.ClientDto;
import pl.tspga.rest.tspgarest.dto.LocationDto;
import pl.tspga.rest.tspgarest.dto.ServicePointDto;
import pl.tspga.rest.tspgarest.dto.TargetLocationsDto;
import pl.tspga.rest.tspgarest.entities.ClientEntity;
import pl.tspga.rest.tspgarest.entities.ServicePointsEntity;
import pl.tspga.rest.tspgarest.ga.DriverPosition;
import pl.tspga.rest.tspgarest.ga.GeneticAlgorithmService;
import pl.tspga.rest.tspgarest.ga.Vertex;
import pl.tspga.rest.tspgarest.repositories.ClientsRepository;
import pl.tspga.rest.tspgarest.repositories.ServicePointsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.tspga.rest.tspgarest.ga.GeneticAlgorithmService.vertexMostRecentResults;

@RestController
public class ClientController {



    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    ServicePointsRepository servicePointsRepository;

    @Autowired
    AsyncComponent asyncComponent;

    boolean isGaAlgorithmStarted = false;

    @RequestMapping(path = "/sendCoordinates", method = RequestMethod.POST)
    public Boolean setDriverCoordinates(@RequestParam("driverLongitude") Double xCord,
                                        @RequestParam("driverLatitude") Double yCord) {
        System.out.println("sendCoordinates thread: " + Thread.currentThread().getId());
        return DriverPosition.setDriverCords(new LocationDto(xCord, yCord));
    }


    @RequestMapping(path = "/getClientData", method = RequestMethod.GET)
    public TargetLocationsDto getClientAndServicesPoints(@RequestParam("id") Long clientId) {

        ClientEntity clientEntity = clientsRepository.getOne(clientId);

        List<ServicePointsEntity> servicePointsEntities = clientEntity.getServicePointsList();

        TargetLocationsDto targetLocationsDto = new TargetLocationsDto();

        List<ServicePointDto> servicePointDtoList = new ArrayList<>();
        for (ServicePointsEntity servicePointsEntity : servicePointsEntities) {
            ServicePointDto servicePointDto = new ServicePointDto().toDto(servicePointsEntity);
            servicePointDtoList.add(servicePointDto);
        }

        targetLocationsDto.setClientServicePoints(servicePointDtoList);
        targetLocationsDto.setClientData(new ClientDto().toDto(clientEntity));

        return targetLocationsDto;
    }


//    @RequestMapping(path = "/saveDriverPoint", method = RequestMethod.POST)
//    public double saveCurrentDriverPoint(@RequestBody LocationDto driverLocationDto) {
//
//        return 1L;
//    }


    @RequestMapping(value = "/startFindingWay")
    public void startAlgorithm(@RequestParam("id") Long clientId) {


        ClientEntity clientEntity = clientsRepository.getOne(clientId);
        List<ServicePointsEntity> servicePointsEntities = clientEntity.getServicePointsList();

        List<Vertex> initialVertexList = initializeVertexList(clientEntity, servicePointsEntities);

        Thread.currentThread();
        System.out.println("startAlgorithm thread: " + Thread.currentThread().getId());

        if(!isGaAlgorithmStarted){
            asyncComponent.startGaAlgorithmAsync(initialVertexList);
            isGaAlgorithmStarted = true;
        }

    }

    @RequestMapping(value = "/stopFindingWay")
    public void stopAlgorithm() {
        GeneticAlgorithmService.breakLoop.set(true);
    }

    @RequestMapping(value = "/resumeFindingWay")
    public void resumeAlgorithm() {
        GeneticAlgorithmService.breakLoop.set(false);
    }

    @RequestMapping(value = "/getMostRecentResults", method = RequestMethod.GET)
    public List<LocationDto> getMostRecentResults() {

        System.out.println("getMostRecentResults: " + Arrays.asList(vertexMostRecentResults));
        return convertVertexListToLocationDtoList(vertexMostRecentResults);

    }


    private List<LocationDto> convertVertexListToLocationDtoList(List<Vertex> vertexList) {

        List<LocationDto> locationDtoList = new ArrayList<>();

        for (Vertex vertex : vertexList) {
            LocationDto locationDto = new LocationDto();
            locationDto.setCordinateX(vertex.getX());
            locationDto.setCordinateY(vertex.getY());
            locationDtoList.add(locationDto);
        }

        return locationDtoList;
    }

    private List<Vertex> initializeVertexList(ClientEntity clientEntity, List<ServicePointsEntity> servicePointsEntities){


        List<Vertex> vertexList = new ArrayList<>();

        // add driver point
        Vertex driverPosition = new Vertex(DriverPosition.driverCords.getCordinateX(), DriverPosition.driverCords.getCordinateY());
        vertexList.add(driverPosition);

        //add service points
        for (ServicePointsEntity servicePointsEntity : servicePointsEntities) {
            Vertex servicePointPosition = new Vertex(servicePointsEntity.getCordinateX(), servicePointsEntity.getCordinateY());
            vertexList.add(servicePointPosition);
        }

        //add client point
        Vertex clientPosition = new Vertex(clientEntity.getCordinateX(), clientEntity.getCordinateY());
        vertexList.add(clientPosition);

        return vertexList;
    }

    @PostMapping(path = "/addClient")
    public void addClient(@RequestBody ClientDto clientDto) {

        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setFirstName(clientDto.getFirstName());
        clientEntity.setLastName(clientDto.getLastName());
        clientEntity.setCordinateX(clientDto.getClientLocation().getCordinateX());
        clientEntity.setCordinateY(clientDto.getClientLocation().getCordinateY());

        clientsRepository.save(clientEntity);
    }

    @RequestMapping(path = "/addServicePoint", method = RequestMethod.POST)
    public void addServicePoint(@RequestHeader("id") Long clientId, @RequestBody ServicePointDto servicePointDto) {

        ServicePointsEntity servicePointsEntity = new ServicePointsEntity();

        ClientEntity clientEntity = clientsRepository.getOne(clientId);

        servicePointsEntity.setClient(clientEntity);
        servicePointsEntity.setName(servicePointDto.getName());
        servicePointsEntity.setDetails(servicePointDto.getDetails());
        servicePointsEntity.setCordinateX(servicePointDto.getLocationDto().getCordinateX());
        servicePointsEntity.setCordinateY(servicePointDto.getLocationDto().getCordinateY());

        servicePointsRepository.save(servicePointsEntity);

    }

}
