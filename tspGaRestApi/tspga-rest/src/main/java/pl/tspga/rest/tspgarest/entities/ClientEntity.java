package pl.tspga.rest.tspgarest.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<ServicePointsEntity> servicePointsList;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cordinateX")
    private double cordinateX;

    @Column(name = "cordinateY")
    private double cordinateY;


    public List<ServicePointsEntity> getServicePointsList() {
        return servicePointsList;
    }

    public void setServicePointsList(List<ServicePointsEntity> servicePointsList) {
        this.servicePointsList = servicePointsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
