package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ReservaSedeService.class)
public class ReservaSedeServiceTest {

  @Autowired
  private ReservaSedeService reservaSedeService;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private SedeRepository sedeRepository;

  private PodamFactory factory = new PodamFactoryImpl();

  private ReservaEntity reservaEntity = new ReservaEntity();
  private SedeEntity sedeEntity = new SedeEntity();
  
  // Configuración inicial
  @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

  private void clearData() {
    entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
  }

  private void insertData() {
		reservaEntity = factory.manufacturePojo(ReservaEntity.class);
		entityManager.persist(reservaEntity);

    sedeEntity.getReservas().add(reservaEntity);
    reservaEntity.setSede(sedeEntity);
  }

  // Prueba de a►4adir una sede
  @Test
  void testAddSede() throws EntityNotFoundException {
    SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
    entity.getReservas();
    sedeRepository.save(entity);

    SedeEntity sedeEntity = reservaSedeService.addSede(entity.getId(), reservaEntity.getId());
    assertNotNull(sedeEntity);
    assertEquals(entity.getId(), sedeEntity.getId());
    assertEquals(entity.getNombre(), sedeEntity.getNombre());
    assertEquals(entity.getDireccion(), sedeEntity.getDireccion());
    assertEquals(entity.getTelefono(), sedeEntity.getTelefono());
    assertEquals(entity.getInstagram(), sedeEntity.getInstagram());
    assertEquals(entity.getCiudad(), sedeEntity.getCiudad());
  }

  // Prueba para añadir una sede con una reserva inválida
  @Test
  void testAddSedeInvalidReserva() {
    assertThrows(EntityNotFoundException.class, () -> {
      SedeEntity newSede = factory.manufacturePojo(SedeEntity.class);
      sedeEntity.getReservas();
      sedeRepository.save(newSede);
      reservaSedeService.addSede(newSede.getId(), 0L);
    });
  }

  // Prueba para añadir una sede invalida
  @Test
    void testAddInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            reservaSedeService.addSede(0L, reservaEntity.getId());
        });
    }
}
