package pl.tspga.rest.tspgarest.dto;

public class LocationDto {

    private double cordinateX;
    private double cordinateY;

    public double getCordinateX() {
        return cordinateX;
    }

    public void setCordinateX(double cordinateX) {
        this.cordinateX = cordinateX;
    }

    public double getCordinateY() {
        return cordinateY;
    }

    public void setCordinateY(double cordinateY) {
        this.cordinateY = cordinateY;
    }

    public LocationDto() {

    }

    public LocationDto(double cordinateX, double cordinateY) {
        this.cordinateX = cordinateX;
        this.cordinateY = cordinateY;
    }

}
