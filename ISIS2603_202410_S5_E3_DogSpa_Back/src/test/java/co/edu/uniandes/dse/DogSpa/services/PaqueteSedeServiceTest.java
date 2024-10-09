package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PaqueteSedeService.class)
class PaqueteSedeServiceTest {
    
    @Autowired
    private PaqueteSedeService paqueteSedeService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<SedeEntity> sedeList = new ArrayList<>();
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
           //SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
		   //paquete.getSedes().add(sedeEntity);
		   entityManager.persist(paquete);
           
            for (int i = 0; i < 3; i++) {
                SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
                sedeEntity.getPaquetesDeServicios().add(paquete);
                entityManager.persist(sedeEntity);
                sedeList.add(sedeEntity);
				paquete.getSedes().add(sedeEntity);
				
            }	
			
        }
    
        private void clearData() {
           
            entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
            entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
        }

    
    /**
	 * Prueba para asociar una sede a un paquete.
	 *
	 */
	@Test
	void testAddSede() throws EntityNotFoundException, IllegalOperationException {
		PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
		entityManager.persist(newPaquete);
		
		SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);
		
		paqueteSedeService.addSede(newPaquete.getId(), sede.getId());
		
		SedeEntity lastSede = paqueteSedeService.getSede(newPaquete.getId(),sede.getId());
		assertEquals(sede.getId(), lastSede.getId());
        assertEquals(sede.getNombre(), lastSede.getNombre());
        assertEquals(sede.getDireccion(), lastSede.getDireccion());
        assertEquals(sede.getTelefono(), lastSede.getTelefono());
        assertEquals(sede.getInstagram(), lastSede.getInstagram());
		assertEquals(sede.getCiudad(), lastSede.getCiudad());
	}
	
	/**
	 * Prueba para asociar una sede que no existe a un paquete.
	 *
	 */
	@Test
	void testAddInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(newPaquete);
			paqueteSedeService.addSede(newPaquete.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar una sede a un paquete que no existe.
	 *
	 */
	@Test
	void testAddSedeInvalidPaquete() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			paqueteSedeService.addSede(0L, sede.getId());
		});
	}

	/**
	 * Prueba para consultar la lista de sedes de un paquete.
	 */
	@Test
	void testGetSedes() throws EntityNotFoundException {
		List<SedeEntity> sedeEntities = paqueteSedeService.getSedeEntities(paquete.getId());

		assertEquals(sedeList.size(), sedeEntities.size());

		for (int i = 0; i < sedeList.size(); i++) {
			assertTrue(sedeEntities.contains(sedeList.get(0)));
		}
	}
	
	/**
	 * Prueba para consultar la lista de sedes de un paquete que no existe.
	 */
	@Test
	void testGetSedesInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteSedeService.getSedeEntities(0L);
		});
	}

	/**
	 * Prueba para consultar una sede de un paquete.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetSede() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity = sedeList.get(0);
		SedeEntity sede = paqueteSedeService.getSede(paquete.getId(), sedeEntity.getId());
		assertNotNull(sede);

		assertEquals(sedeEntity.getId(), sede.getId());
		assertEquals(sede.getNombre(), sede.getNombre());
        assertEquals(sede.getDireccion(), sede.getDireccion());
        assertEquals(sede.getTelefono(), sede.getTelefono());
        assertEquals(sede.getInstagram(), sede.getInstagram());
		assertEquals(sede.getCiudad(), sede.getCiudad());
	}
	
	/**
	 * Prueba para consultar una sede que no existe de un paquete.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteSedeService.getSede(paquete.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para consultar una sede de un paquete que no existe.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetSedeInvalidPaquete() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sedeEntity = sedeList.get(0);
			paqueteSedeService.getSede(0L, sedeEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una sede no asociada a un paquete.
	 *
	 */
	@Test
	void testGetNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, ()->{
			PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(newPaquete);

			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			paqueteSedeService.getSede(newPaquete.getId(), sede.getId());
		});
	}

	/**
	 * Prueba para actualizar las sedes de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceSedes() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			paquete.getSedes().add(entity);
			nuevaLista.add(entity);
		}
		paqueteSedeService.replaceSedes(paquete.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = paqueteSedeService.getSedeEntities(paquete.getId());
		for (SedeEntity sede : nuevaLista) {
			assertTrue(sedeEntities.contains(sede));
		}
	}
	
	/**
	 * Prueba para actualizar las sedes de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceSedes2() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		paqueteSedeService.replaceSedes(paquete.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = paqueteSedeService.getSedeEntities(paquete.getId());
		for (SedeEntity sede : nuevaLista) {
			assertTrue(sedeEntities.contains(sede));
		}
	}
	
	
	/**
	 * Prueba para actualizar las sedes de un paquete que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getPaquetesDeServicios().add(paquete);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paqueteSedeService.replaceSedes(0L, nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar las sedes que no existen de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceInvalidSedes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			paqueteSedeService.replaceSedes(paquete.getId(), nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar una sede de un paquete que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getPaquetesDeServicios().add(paquete);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paqueteSedeService.replaceSedes(0L, nuevaLista);
		});
	}

	/**
	 * Prueba desasociar una sede de un paquete.
	 *
	 */
	@Test
	void testRemoveSede() throws EntityNotFoundException {
		for (SedeEntity sede : sedeList) {
			paqueteSedeService.removeSede(paquete.getId(),sede.getId());;
		}
		assertTrue(paqueteSedeService.getSedeEntities(paquete.getId()).isEmpty());
	}
	
	/**
	 * Prueba desasociar una sede que no existe de un paquete.
	 *
	 */
	@Test
	void testRemoveInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteSedeService.removeSede(paquete.getId(),0L);;
		});
	}
	
	/**
	 * Prueba desasociar una sede de un paquete que no existe.
	 *
	 */
	@Test
	void testRemoveAuthorInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteSedeService.removeSede(0L, sedeList.get(0).getId());
		});
	}
}
