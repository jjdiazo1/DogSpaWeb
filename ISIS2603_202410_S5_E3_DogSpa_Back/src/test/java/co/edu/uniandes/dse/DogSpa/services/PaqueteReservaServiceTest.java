package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PaqueteReservaService.class)

class PaqueteReservaServiceTest{
    

    @Autowired
    private PaqueteReservaService paqueteReservaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ReservaEntity> reservaList = new ArrayList<>();
    private PaqueteEntity paquete = new PaqueteEntity();

     /**
         * Configuración inicial de la prueba.
         */
        @BeforeEach
        void setUp() {
                    clearData();
                    insertData();
            }
    
        private void insertData() {
    
           paquete = factory.manufacturePojo(PaqueteEntity.class);
		   		entityManager.persist(paquete);
           
            for (int i = 0; i < 3; i++) {
                ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
                reservaEntity.getPaquetes().add(paquete);
                entityManager.persist(reservaEntity);
                reservaList.add(reservaEntity);
				paquete.getPaquetesDeLaReserva().add(reservaEntity);
				
            }	
			
        }
    
        private void clearData() {
           
            entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
            entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
        }

    
    /**
	 * Prueba para asociar una reserva a un paquete.
	 *
	 */
	@Test
	void testAddReserva() throws EntityNotFoundException, IllegalOperationException {
		PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
		entityManager.persist(newPaquete);
		
		ReservaEntity reserva = factory.manufacturePojo(ReservaEntity.class);
		entityManager.persist(reserva);
		
		paqueteReservaService.addReserva(newPaquete.getId(), reserva.getId());
		
		ReservaEntity lastReserva = paqueteReservaService.getReserva(newPaquete.getId(),reserva.getId());
		assertEquals(reserva.getId(), lastReserva.getId());
        assertEquals(reserva.getNombreMascota(), lastReserva.getNombreMascota());
        assertEquals(reserva.getDuenoMascota(), lastReserva.getDuenoMascota());
        assertEquals(reserva.getTelefono(), lastReserva.getTelefono());
        assertEquals(reserva.getFecha(), lastReserva.getFecha());
		assertEquals(reserva.getTrabajadorEncargado(), lastReserva.getTrabajadorEncargado());
        assertEquals(reserva.getTipoMascota(), lastReserva.getTipoMascota());
        assertEquals(reserva.getRazaMascota(), lastReserva.getRazaMascota());
        assertEquals(reserva.getEdadMascota(), lastReserva.getEdadMascota());
	}
	
	/**
	 * Prueba para asociar una reserva que no existe a un paquete.
	 *
	 */
	@Test
	void testAddInvalidReserva() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(newPaquete);
			paqueteReservaService.addReserva(newPaquete.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar una reserva a un paquete que no existe.
	 *
	 */
	@Test
	void testAddReservaInvalidPaquete() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			ReservaEntity reserva = factory.manufacturePojo(ReservaEntity.class);
			entityManager.persist(reserva);
			paqueteReservaService.addReserva(0L, reserva.getId());
		});
	}

	/**
	 * Prueba para consultar la lista de reservas de un paquete.
	 */
	@Test
	void testGetReservas() throws EntityNotFoundException {
		List<ReservaEntity> reservaEntities = paqueteReservaService.getPaquetesDeLaReserva(paquete.getId());

		assertEquals(reservaList.size(), reservaEntities.size());

		for (int i = 0; i < reservaList.size(); i++) {
			assertTrue(reservaEntities.contains(reservaList.get(0)));
		}
	}
	
	/**
	 * Prueba para consultar la lista de reservas de un paquete que no existe.
	 */
	@Test
	void testGetReservasInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteReservaService.getPaquetesDeLaReserva(0L);
		});
	}

	

	/**
	 * Prueba desasociar una reserva de un paquete.
	 *
	 */
	@Test
	void testRemoveReserva() throws EntityNotFoundException {
		for (ReservaEntity reserva : reservaList) {
			paqueteReservaService.removeReserva(paquete.getId(),reserva.getId());;
		}
		assertTrue(paqueteReservaService.getPaquetesDeLaReserva(paquete.getId()).isEmpty());
	}
	
	/**
	 * Prueba desasociar una reserva que no existe de un paquete.
	 *
	 */
	@Test
	void testRemoveInvalidReserva(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteReservaService.removeReserva(paquete.getId(),0L);;
		});
	}
	
	/**
	 * Prueba desasociar una reserva de un paquete que no existe.
	 *
	 */
	@Test
	void testRemoveAuthorInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteReservaService.removeReserva(0L, reservaList.get(0).getId());
		});
	}
}
