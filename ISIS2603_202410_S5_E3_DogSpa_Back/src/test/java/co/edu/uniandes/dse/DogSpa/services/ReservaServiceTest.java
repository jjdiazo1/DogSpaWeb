package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ReservaService.class)
public class ReservaServiceTest {
  
  @Autowired
  private ReservaService reservaService;

  @Autowired
  private TestEntityManager entityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  private List<ReservaEntity> reservaList = new ArrayList<>();

  @BeforeEach
    void setUp() {
      clearData();
      insertData();
    }

    // Limpiando datos
    private void clearData() {
      entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
    }

    // Insertando valores
    private void insertData() {
      for (int i = 0; i < 3; i++) {
        ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
        entityManager.persist(reservaEntity);
        reservaList.add(reservaEntity);
      }
    }

    @Test
    void testCreateReserva() throws EntityNotFoundException,IllegalOperationException {
      ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
      SedeEntity temporalSede = factory.manufacturePojo(SedeEntity.class);

      newEntity.setSede(temporalSede); // Creo una sede que no sea null
      LocalDate date = LocalDate.of(2024, 12, 12);
      LocalTime time = LocalTime.of(12, 30, 30);
      LocalDateTime dateTime = LocalDateTime.of(date, time);
      newEntity.setFecha(dateTime);
      ReservaEntity result = reservaService.createReserva(newEntity);
      assertNotNull(result);

      ReservaEntity entity = entityManager.find(ReservaEntity.class, result.getId());

      assertEquals(newEntity.getId(), entity.getId());
      assertEquals(newEntity.getNombreMascota(), entity.getNombreMascota());
      assertEquals(newEntity.getDuenoMascota(), entity.getDuenoMascota());
      assertEquals(newEntity.getTelefono(), entity.getTelefono());
      assertEquals(newEntity.getFecha(), entity.getFecha());
      assertEquals(newEntity.getTrabajadorEncargado(), entity.getTrabajadorEncargado());
      assertEquals(newEntity.getTipoMascota(), entity.getTipoMascota());
      assertEquals(newEntity.getRazaMascota(), entity.getRazaMascota());
      assertEquals(newEntity.getEdadMascota(), entity.getEdadMascota());
    }

    // Prueba de obtener todas las reservas
    @Test
    void testGetReservas() {
      List <ReservaEntity> reservas = reservaService.getReservas();
      assertEquals(reservaList.size(), reservas.size());
      for (ReservaEntity entity : reservas) {
        boolean found = false;
        for (ReservaEntity storedEntity : reservaList) {
          if (entity.getId().equals(storedEntity.getId())){
            found = true;
          }
        }
        assertTrue(found);
      }
    }

    // Prueba de obtener una reserva
    @Test
    void testGetReserva() throws EntityNotFoundException {
      ReservaEntity entity = reservaList.get(0);
      ReservaEntity resp = reservaService.getReserva(entity.getId());
      assertNotNull(resp);
      assertEquals(entity.getId(), resp.getId());
      assertEquals(entity.getNombreMascota(), resp.getNombreMascota());
      assertEquals(entity.getDuenoMascota(), resp.getDuenoMascota());
      assertEquals(entity.getTelefono(), resp.getTelefono());
      assertEquals(entity.getFecha(), resp.getFecha());
      assertEquals(entity.getTrabajadorEncargado(), resp.getTrabajadorEncargado());
      assertEquals(entity.getTipoMascota(), resp.getTipoMascota());
      assertEquals(entity.getRazaMascota(), resp.getRazaMascota());
      assertEquals(entity.getEdadMascota(), resp.getEdadMascota());
    }

    // Prueba de actualizar una reserva
    @Test
    void testUpdateReserva() throws EntityNotFoundException, IllegalOperationException {
      ReservaEntity reservaEntity = reservaList.get(0);
      ReservaEntity pojoEntity = factory.manufacturePojo(ReservaEntity.class);

      pojoEntity.setId(reservaEntity.getId());
      reservaService.updateReserva(reservaEntity.getId(), pojoEntity);

      ReservaEntity resp = entityManager.find(ReservaEntity.class, reservaEntity.getId());
      assertEquals(pojoEntity.getId(), resp.getId());
      assertEquals(pojoEntity.getNombreMascota(), resp.getNombreMascota());
      assertEquals(pojoEntity.getDuenoMascota(), resp.getDuenoMascota());
      assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
      assertEquals(pojoEntity.getFecha(), resp.getFecha());
      assertEquals(pojoEntity.getTrabajadorEncargado(), resp.getTrabajadorEncargado());
      assertEquals(pojoEntity.getTipoMascota(), resp.getTipoMascota());
      assertEquals(pojoEntity.getRazaMascota(), resp.getRazaMascota());
      assertEquals(pojoEntity.getEdadMascota(), resp.getEdadMascota());
    }

    // Prueba de borrar una reserva
    @Test
    void testDeleteReserva() throws EntityNotFoundException {
      ReservaEntity reservaEntity = reservaList.get(0);
      reservaService.deleteReserva(reservaEntity.getId());

      ReservaEntity deleted = entityManager.find(ReservaEntity.class, reservaEntity.getId());
      assertNull(deleted);
    }

    // Insertando una fecha antes de la actual
    @Test
    void testDateBefore() {
      assertThrows(IllegalOperationException.class, () -> {
        ReservaEntity pojoEntity = factory.manufacturePojo(ReservaEntity.class);
        SedeEntity temporalSede = factory.manufacturePojo(SedeEntity.class);
        pojoEntity.setSede(temporalSede);
        LocalDate date = LocalDate.of(2017, 12, 12);
        LocalTime time = LocalTime.of(8, 30);
        LocalDateTime fechaYHora = LocalDateTime.of(date, time);
        pojoEntity.setFecha(fechaYHora);
        reservaService.createReserva(pojoEntity);
      });
    }
}
