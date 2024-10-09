package co.edu.uniandes.dse.DogSpa.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;



@Repository
public interface SedeRepository extends JpaRepository<SedeEntity, Long> {
    List<SedeEntity> findByCiudadIgnoreCase(String ciudad);
}