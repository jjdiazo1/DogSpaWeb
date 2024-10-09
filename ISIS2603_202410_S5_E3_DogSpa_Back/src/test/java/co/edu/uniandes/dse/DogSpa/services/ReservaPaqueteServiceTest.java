package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ReservaPaqueteService.class)
public class ReservaPaqueteServiceTest {
  
  @Autowired
  private ReservaPaqueteService reservaPaqueteService;

  @Autowired
  private TestEntityManager entityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  // Una reserva puede tener muchos paquetes
  private ReservaEntity reserva = new ReservaEntity();
  private List<PaqueteEntity> paquetes = new ArrayList<>();

  @BeforeEach 
    void setUp() {
      clearData();
      insertData();
    }

    private void clearData() {
      entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
      entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
    }

    private void insertData() {
      reserva = factory.manufacturePojo(ReservaEntity.class);
      entityManager.persist(reserva);
      
      for (int i = 0; i < 3; i++) {
        PaqueteEntity paquete = factory.manufacturePojo(PaqueteEntity.class);
        paquete.getPaquetesDeLaReserva().add(reserva);
        entityManager.persist(paquete);
        paquetes.add(paquete);
        reserva.getPaquetes().add(paquete);
       }	
    }

    // Prueba de asociar un paquete a una reserva
    @Test
    void testAddPaquete() throws EntityNotFoundException, IllegalOperationException {
      PaqueteEntity paqueteEntity = factory.manufacturePojo(PaqueteEntity.class);
      entityManager.persist(paqueteEntity);

      ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
      entityManager.persist(reservaEntity);

      reservaPaqueteService.addPaquete(paqueteEntity.getId(), reservaEntity.getId());

      PaqueteEntity lastPaquete = reservaPaqueteService.getPaquete(paqueteEntity.getId(), reservaEntity.getId());
      assertNotNull(lastPaquete);

      assertEquals(paqueteEntity.getId(), lastPaquete.getId());
      assertEquals(paqueteEntity.getNombre(), lastPaquete.getNombre());
      assertEquals(paqueteEntity.getPaquetesDeLaReserva(), lastPaquete.getPaquetesDeLaReserva());
      assertEquals(paqueteEntity.getPrecio(), lastPaquete.getPrecio());
      assertEquals(paqueteEntity.getSedes(), lastPaquete.getSedes());
      assertEquals(paqueteEntity.getServicios(), lastPaquete.getServicios());
    }

    // Asociar una reserva un paquete que no existe
    @Test
    void testAddInvalidPaquete() {
      assertThrows(EntityNotFoundException.class, ()->{
        ReservaEntity newReserva = factory.manufacturePojo(ReservaEntity.class);
        entityManager.persist(newReserva);
        reservaPaqueteService.addPaquete(0L, newReserva.getId());
      });
	  }

    // Asociar un paquete a una reserva que no existe
    @Test
    void testAddInvalidReserva() {
      assertThrows(EntityNotFoundException.class, ()->{
        PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
        entityManager.persist(newPaquete);
        reservaPaqueteService.addPaquete(newPaquete.getId(), 0L);
      });
	  }

    // Consultar la lista de paquetes de una reserva
    @Test
    void testGetPaquetes() throws EntityNotFoundException {
      List<PaqueteEntity> newPaquetes = reservaPaqueteService.getReservasDelPaquete(reserva.getId());
      assertEquals(paquetes.size(), newPaquetes.size());

      for (int i = 0; i < paquetes.size(); i++) {
        assertTrue(newPaquetes.contains(paquetes.get(0)));
      }
    }

    // Desasociar un paquete de una reserva
    @Test
    void testRemovePaquete() throws EntityNotFoundException {
      for (PaqueteEntity paquete : paquetes) {
        reservaPaqueteService.removePaquete(paquete.getId(),reserva.getId());
      }
      assertTrue(reservaPaqueteService.getReservasDelPaquete(reserva.getId()).isEmpty());
    }

    // Prueba desasociar un paquete a una reserva que no existe
    @Test
    void testRemoveInvalidPaquete(){
      assertThrows(EntityNotFoundException.class, ()->{
        reservaPaqueteService.removePaquete(0L, reserva.getId());
      });
    }
}
