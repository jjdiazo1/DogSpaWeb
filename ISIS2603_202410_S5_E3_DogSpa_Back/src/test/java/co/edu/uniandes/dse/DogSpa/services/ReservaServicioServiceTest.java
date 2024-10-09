package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ReservaServicioService.class)
public class ReservaServicioServiceTest {
  
  @Autowired 
  private ReservaServicioService reservaServicioService;

  @Autowired
  private TestEntityManager entityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  // Muchos servicios tienen muchas reservas
  private ReservaEntity reserva = new ReservaEntity();
  private List<ServicioEntity> servicios = new ArrayList<>();

  @BeforeEach 
    void setUp() {
      clearData();
      insertData();
    }

    private void clearData() {
      entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
      entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
    }

    private void insertData() {
      reserva = factory.manufacturePojo(ReservaEntity.class);
      entityManager.persist(reserva);

      for (int i = 0; i < 3; i++) {
        ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
        servicioEntity.setServiciosDeReserva(reserva);
        entityManager.persist(servicioEntity);
        servicios.add(servicioEntity);
        reserva.getServicios().add(servicioEntity);
      }
    }

    @Test
    void testAddServicio() throws EntityNotFoundException, IllegalOperationException {
      ReservaEntity reserva = factory.manufacturePojo(ReservaEntity.class);
      entityManager.persist(reserva);

      ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
		  entityManager.persist(servicio);

      reservaServicioService.addServicio(servicio.getId(), reserva.getId());

      ServicioEntity newServicio = reservaServicioService.getServicio(reserva.getId(), servicio.getId());
      assertEquals(servicio.getId(), newServicio.getId());
      assertEquals(servicio.getNombre(), newServicio.getNombre());
      assertEquals(servicio.getPrecio(), newServicio.getPrecio());
    }

    @Test
    void testGetServicios() throws EntityNotFoundException {
      List<ServicioEntity> newServicios = reservaServicioService.getServicios(reserva.getId());
      assertEquals(servicios.size(), newServicios.size());

      for (int i = 0; i < servicios.size(); i++) {
        assertTrue(newServicios.contains(servicios.get(0)));
      }
    }

    @Test
    void testAddInvalidServicio() {
      assertThrows(EntityNotFoundException.class, ()->{
        ReservaEntity newReserva = factory.manufacturePojo(ReservaEntity.class);
        entityManager.persist(newReserva);
        reservaServicioService.addServicio(0L, newReserva.getId());
      });
    }
}
