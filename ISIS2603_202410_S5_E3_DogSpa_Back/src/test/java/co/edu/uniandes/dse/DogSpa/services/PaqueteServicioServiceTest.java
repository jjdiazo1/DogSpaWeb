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

import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PaqueteServicioService.class)

class PaqueteServicioServiceTest{
    
    @Autowired
    private PaqueteServicioService paqueteServicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioEntity> servicioList = new ArrayList<>();
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
           //ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
		   //paquete.getServicios().add(servicioEntity);
		   entityManager.persist(paquete);
           
            for (int i = 0; i < 3; i++) {
                ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
                servicioEntity.getPaquetes().add(paquete);
                entityManager.persist(servicioEntity);
                servicioList.add(servicioEntity);
				paquete.getServicios().add(servicioEntity);
				
            }	
			
        }
    
        private void clearData() {
           
            entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
            entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        }

    
    /**
	 * Prueba para asociar un servicio a un paquete.
	 *
	 */
	@Test
	void testAddServicio() throws EntityNotFoundException, IllegalOperationException {
		PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
		entityManager.persist(newPaquete);
		
		ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
		entityManager.persist(servicio);
		
		paqueteServicioService.addServicio(newPaquete.getId(), servicio.getId());
		
		ServicioEntity lastServicio = paqueteServicioService.getServicio(newPaquete.getId(),servicio.getId());
		assertEquals(servicio.getId(), lastServicio.getId());
        assertEquals(servicio.getNombre(), lastServicio.getNombre());
        assertEquals(servicio.getPrecio(), lastServicio.getPrecio());
        
	}
	
	/**
	 * Prueba para asociar un servicio que no existe a un paquete.
	 *
	 */
	@Test
	void testAddInvalidServicio() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(newPaquete);
			paqueteServicioService.addServicio(newPaquete.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar un servicio a un paquete que no existe.
	 *
	 */
	@Test
	void testAddServicioInvalidPaquete() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(servicio);
			paqueteServicioService.addServicio(0L, servicio.getId());
		});
	}

	/**
	 * Prueba para consultar la lista de servicios de un paquete.
	 */
	@Test
	void testGetServicios() throws EntityNotFoundException {
		List<ServicioEntity> servicioEntities = paqueteServicioService.getServicios(paquete.getId());

		assertEquals(servicioList.size(), servicioEntities.size());

		for (int i = 0; i < servicioList.size(); i++) {
			assertTrue(servicioEntities.contains(servicioList.get(0)));
		}
	}
	
	/**
	 * Prueba para consultar la lista de servicios de un paquete que no existe.
	 */
	@Test
	void testGetServiciosInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteServicioService.getServicios(0L);
		});
	}

	/**
	 * Prueba para consultar un servicio de un paquete.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetServicio() throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = servicioList.get(0);
		ServicioEntity servicio = paqueteServicioService.getServicio(paquete.getId(), servicioEntity.getId());
		assertNotNull(servicio);

		assertEquals(servicioEntity.getId(), servicio.getId());
		assertEquals(servicio.getNombre(), servicio.getNombre());
        assertEquals(servicio.getPrecio(), servicio.getPrecio());

	}
	
	/**
	 * Prueba para consultar un servicio que no existe de un paquete.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetInvalidServicio()  {
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteServicioService.getServicio(paquete.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para consultar un servicio de un paquete que no existe.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetServicioInvalidPaquete() {
		assertThrows(EntityNotFoundException.class, ()->{
			ServicioEntity servicioEntity = servicioList.get(0);
			paqueteServicioService.getServicio(0L, servicioEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener un servicio no asociada a un paquete.
	 *
	 */
	@Test
	void testGetNotAssociatedServicio() {
		assertThrows(IllegalOperationException.class, ()->{
			PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(newPaquete);

			ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(servicio);
			paqueteServicioService.getServicio(newPaquete.getId(), servicio.getId());
		});
	}

	/**
	 * Prueba para actualizar las servicios de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceServicios() throws EntityNotFoundException {
		List<ServicioEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(entity);
			paquete.getServicios().add(entity);
			nuevaLista.add(entity);
		}
		paqueteServicioService.replaceServicios(paquete.getId(), nuevaLista);
		
		List<ServicioEntity> servicioEntities = paqueteServicioService.getServicios(paquete.getId());
		for (ServicioEntity servicio : nuevaLista) {
			assertTrue(servicioEntities.contains(servicio));
		}
	}
	
	/**
	 * Prueba para actualizar las servicios de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceServicios2() throws EntityNotFoundException {
		List<ServicioEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		paqueteServicioService.replaceServicios(paquete.getId(), nuevaLista);
		
		List<ServicioEntity> servicioEntities = paqueteServicioService.getServicios(paquete.getId());
		for (ServicioEntity servicio : nuevaLista) {
			assertTrue(servicioEntities.contains(servicio));
		}
	}
	
	
	/**
	 * Prueba para actualizar las servicios de un paquete que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<ServicioEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
				entity.getPaquetes().add(paquete);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paqueteServicioService.replaceServicios(0L, nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar las servicios que no existen de un paquete.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceInvalidServicios() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<ServicioEntity> nuevaLista = new ArrayList<>();
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			paqueteServicioService.replaceServicios(paquete.getId(), nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar un servicio de un paquete que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidServicio(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<ServicioEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
				entity.getPaquetes().add(paquete);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paqueteServicioService.replaceServicios(0L, nuevaLista);
		});
	}

	/**
	 * Prueba desasociar un servicio de un paquete.
	 *
	 */
	@Test
	void testRemoveServicio() throws EntityNotFoundException {
		for (ServicioEntity servicio : servicioList) {
			paqueteServicioService.removeServicio(paquete.getId(),servicio.getId());;
		}
		assertTrue(paqueteServicioService.getServicios(paquete.getId()).isEmpty());
	}
	
	/**
	 * Prueba desasociar un servicio que no existe de un paquete.
	 *
	 */
	@Test
	void testRemoveInvalidServicio(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteServicioService.removeServicio(paquete.getId(),0L);;
		});
	}
	
	/**
	 * Prueba desasociar un servicio de un paquete que no existe.
	 *
	 */
	@Test
	void testRemoveAuthorInvalidPaquete(){
		assertThrows(EntityNotFoundException.class, ()->{
			paqueteServicioService.removeServicio(0L, servicioList.get(0).getId());
		});
	}
}
