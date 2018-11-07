package pl.tspga.rest.tspgarest.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourManager {

    private static List<Vertex> destinationVertexes = new ArrayList<>();
    private static Double[][] distances;

    public static void setDestinationVertexes(List<Vertex> destinationVertexes) {
        int i = 0;
        for(Vertex destinationVertex : destinationVertexes) {
            destinationVertex.id = i++;
            TourManager.destinationVertexes.add(destinationVertex);
        }
        TourManager.destinationVertexes = destinationVertexes;
        calculateDistances();
        //drawArray();
        System.out.println("destinationVertexes: " + Arrays.asList(destinationVertexes));
    }

    public static Vertex getVertex(int index){
        return destinationVertexes.get(index);
    }

    public static int numberOfVertexes(){
        return destinationVertexes.size();
    }

    private static void calculateDistances() {
        int destinationVertexessize = destinationVertexes.size();
        distances = new Double[destinationVertexessize][destinationVertexessize];

        for (int i = 0; i < destinationVertexessize; i++) {
            for (int ii = 0; ii < destinationVertexessize; ii++) {
                if (distances[i][ii] != null) {
                    distances[ii][i] = distances[i][ii];
                } else {
                    double xDistance = Math.abs(getVertex(ii).x - getVertex(i).x);
                    double yDistance = Math.abs(getVertex(ii).y - getVertex(i).y);
                    distances[ii][i] = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
                }
            }
        }

    }

    public static void drawArray() {
        for(Double[] distance : distances) {
            for(Double distance_ : distance) {
                System.out.print(distance_  +  " ");
            }
            System.out.println("");
        }
    }

   public static Double getDistanceBetweenPoints(int id1, int id2) {
       return distances[id1][id2];
    }
}