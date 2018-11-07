package pl.tspga.rest.tspgarest.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GeneticAlgorithmService {

    public static List<Vertex> vertexMostRecentResults = new ArrayList<>();
    public static AtomicBoolean breakLoop = new AtomicBoolean(false);


    public static void startGeneticAlgorithm(List<Vertex> vertexList) {

        System.out.println("starting genetic algorithm");

        TourManager.setDestinationVertexes(vertexList);
        Population population = new Population(15, true);
        System.out.println("Initial distance: " + population.getFittest().getDistance());

        int i = 0;
        while(true){
            if(!breakLoop.get()) {
                population = GenethicAlgorithm.evolvePopulation(population);
                i++;
                if (i == 5000) {
                    System.out.println("Thread ID: " +  Thread.currentThread().getId() + " - " + i + " iteration pass");
                    System.out.println("current distance: " + population.getFittest().getDistance());
                    saveMostRecentResult(population.getFittest().toTour());
                    i = 0;
                }
            }
        }
    }


    private static void saveMostRecentResult(List<Vertex> vertexList) {

        vertexMostRecentResults = vertexList;

    }

}