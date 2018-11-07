package pl.tspga.rest.tspgarest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tspga.rest.tspgarest.entities.ServicePointsEntity;

import java.util.List;

@Repository
public interface ServicePointsRepository extends JpaRepository<ServicePointsEntity, Long> {

    List<ServicePointsEntity> findByCordinateX(Integer coordinateX);
}
