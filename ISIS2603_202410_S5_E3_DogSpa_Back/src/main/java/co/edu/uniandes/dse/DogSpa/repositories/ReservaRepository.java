package co.edu.uniandes.dse.DogSpa.repositories;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

}