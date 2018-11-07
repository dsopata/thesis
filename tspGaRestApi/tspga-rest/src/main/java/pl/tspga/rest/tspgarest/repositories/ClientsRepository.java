package pl.tspga.rest.tspgarest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tspga.rest.tspgarest.entities.ClientEntity;

import java.util.List;

public interface ClientsRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findByFirstName(String firstName);
}
