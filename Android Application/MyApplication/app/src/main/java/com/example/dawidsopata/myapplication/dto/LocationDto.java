package com.example.dawidsopata.myapplication.dto;

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

    @Override
    public String toString() {
        return "LocationDto{" +
                "cordinateX=" + cordinateX +
                ", cordinateY=" + cordinateY +
                '}';
    }
}
