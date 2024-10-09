package co.edu.uniandes.dse.DogSpa.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.DogSpa.entities.RestriccionEntity;

@Repository
public interface RestriccionRepository extends JpaRepository<RestriccionEntity, Long> {
}

