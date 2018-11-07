
package pl.tspga.rest.tspgarest.ga;

public class Vertex {
    int id;
    double x;
    double y;

    public Vertex(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double distanceTo(Vertex vertex){
        return TourManager.getDistanceBetweenPoints(this.id, vertex.id);
    }

    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}