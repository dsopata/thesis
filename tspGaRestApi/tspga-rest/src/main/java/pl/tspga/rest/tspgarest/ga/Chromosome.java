package pl.tspga.rest.tspgarest.ga;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import static pl.tspga.rest.tspgarest.ga.TourManager.numberOfVertexes;

public class Chromosome {

    public static ArrayList bestTour = new ArrayList<Vertex>();
    public ArrayList tour = new ArrayList<Vertex>();

    private double fitness = 0;
    private int distance = 0;
    private Boolean goindBackToStartLocaion = false;

    public Chromosome(){
        for (int i = 0; i < numberOfVertexes(); i++) {
            tour.add(null);
        }
    }

    public Chromosome(ArrayList tour){
        this.tour = tour;
    }

    public void generateIndividual() {
        ArrayList tempTour = new ArrayList<Vertex>();
        int servicePointIndex = numberOfVertexes()-1;

        for (int index = 1; index < servicePointIndex ; index++) {
            tempTour.add(TourManager.getVertex(index));
        }
        Collections.shuffle(tempTour);
        tempTour.add(0, TourManager.getVertex(0));
        tempTour.add(servicePointIndex , TourManager.getVertex(servicePointIndex ));

        fitness = 0;
        distance = 0;
        tour = tempTour;

    }

    public Vertex getVertex(int tourPosition) {
        return (Vertex)tour.get(tourPosition);
    }

    public void setVertex(int tourPosition, Vertex vertex) {
        tour.set(tourPosition, vertex);
        fitness = 0;
        distance = 0;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }

    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            int maxTourSize = goindBackToStartLocaion ? chromosomeSize() : chromosomeSize()-1;
            for (int vertex = 0; vertex < maxTourSize; vertex++) {
                Vertex fromVertex = getVertex(vertex);
                Vertex destinationVertex;
                if(vertex+1 < chromosomeSize()){
                    destinationVertex = getVertex(vertex+1);
                }
                else{
                    destinationVertex = getVertex(0);
                }
                tourDistance += fromVertex.distanceTo(destinationVertex);
            }
            distance = tourDistance;
        }
        return distance;
    }

    public void removeFromTour(int index) {
        tour.remove(index);
    }

    public void addToTour(int index, Vertex obj) {
        tour.add(index, obj);
    }

    public int chromosomeSize() {
        return tour.size();
    }

    public boolean containsVertex(Vertex vertex){
        return tour.contains(vertex);
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < chromosomeSize(); i++) {
            geneString += getVertex(i)+"|";
        }
        return geneString;
    }

    public List<Vertex> toTour() {
        return tour;
    }
}