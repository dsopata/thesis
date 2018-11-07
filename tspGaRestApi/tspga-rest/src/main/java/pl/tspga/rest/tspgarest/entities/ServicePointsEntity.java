package pl.tspga.rest.tspgarest.entities;

import javax.persistence.*;

@Entity(name = "service_points")
public class ServicePointsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity client;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "cordinateX")
    private double cordinateX;

    @Column(name = "cordinateY")
    private double cordinateY;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

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
        return "LocationsEntity{" +
                "id=" + id +
                ", cordinateX=" + cordinateX +
                ", cordinateY=" + cordinateY +
                '}';
    }
}
