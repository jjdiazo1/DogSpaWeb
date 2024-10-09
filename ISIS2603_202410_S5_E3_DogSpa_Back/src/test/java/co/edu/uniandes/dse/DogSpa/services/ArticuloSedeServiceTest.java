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
import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ArticuloSedeService.class)

public class ArticuloSedeServiceTest {
    

    @Autowired
    private ArticuloSedeService articuloSedeService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<SedeEntity> sedeList = new ArrayList<>();
    private ArticuloEntity articulo = new ArticuloEntity();

     /**
         * Configuración inicial de la prueba.
         */
        @BeforeEach
        void setUp() {
                    clearData();
                    insertData();
            }
    
        private void insertData() {
    
           articulo = factory.manufacturePojo(ArticuloEntity.class);
           //SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
		   //articulo.getSedes().add(sedeEntity);
		   entityManager.persist(articulo);
           
            for (int i = 0; i < 3; i++) {
                SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
                sedeEntity.getArticulos().add(articulo);
                entityManager.persist(sedeEntity);
                sedeList.add(sedeEntity);
				articulo.getSedes().add(sedeEntity);
				
            }	
			
        }
    
        private void clearData() {
           
            entityManager.getEntityManager().createQuery("delete from ArticuloEntity").executeUpdate();
            entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
        }

    
    /**
	 * Prueba para asociar una sede a un libro.
	 *
	 */
	@Test
	void testAddSede() throws EntityNotFoundException, IllegalOperationException {
		ArticuloEntity newArticulo = factory.manufacturePojo(ArticuloEntity.class);
		entityManager.persist(newArticulo);
		
		SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);
		
		articuloSedeService.addSede(newArticulo.getId(), sede.getId());
		
		SedeEntity lastSede = articuloSedeService.getSede(newArticulo.getId(),sede.getId());
		assertEquals(sede.getId(), lastSede.getId());
        assertEquals(sede.getNombre(), lastSede.getNombre());
        assertEquals(sede.getDireccion(), lastSede.getDireccion());
        assertEquals(sede.getTelefono(), lastSede.getTelefono());
        assertEquals(sede.getInstagram(), lastSede.getInstagram());
		assertEquals(sede.getCiudad(), lastSede.getCiudad());
	}
	
	/**
	 * Prueba para asociar una sede que no existe a un articulo.
	 *
	 */
	@Test
	void testAddInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ArticuloEntity newArticulo = factory.manufacturePojo(ArticuloEntity.class);
			entityManager.persist(newArticulo);
			articuloSedeService.addSede(newArticulo.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar una sede a un articulo que no existe.
	 *
	 */
	@Test
	void testAddSedeInvalidArticulo() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			articuloSedeService.addSede(0L, sede.getId());
		});
	}

	/**
	 * Prueba para consultar la lista de sede de un articulo.
	 */
	@Test
	void testGetSedes() throws EntityNotFoundException {
		List<SedeEntity> sedeEntities = articuloSedeService.getSedes(articulo.getId());

		assertEquals(sedeList.size(), sedeEntities.size());

		for (int i = 0; i < sedeList.size(); i++) {
			assertTrue(sedeEntities.contains(sedeList.get(0)));
		}
	}
	
	/**
	 * Prueba para consultar la lista de sedes de un articulo que no existe.
	 */
	@Test
	void testGetSedesInvalidArticulo(){
		assertThrows(EntityNotFoundException.class, ()->{
			articuloSedeService.getSedes(0L);
		});
	}

	/**
	 * Prueba para consultar una sede de un articulo.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetSede() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity = sedeList.get(0);
		SedeEntity sede = articuloSedeService.getSede(articulo.getId(), sedeEntity.getId());
		assertNotNull(sede);

		assertEquals(sedeEntity.getId(), sede.getId());
		assertEquals(sede.getNombre(), sede.getNombre());
        assertEquals(sede.getDireccion(), sede.getDireccion());
        assertEquals(sede.getTelefono(), sede.getTelefono());
        assertEquals(sede.getInstagram(), sede.getInstagram());
		assertEquals(sede.getCiudad(), sede.getCiudad());
	}
	
	/**
	 * Prueba para consultar una sede que no existe de un articulo.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			articuloSedeService.getSede(articulo.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para consultar una de un articulo que no existe.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetSedeInvalidArticulo() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sedeEntity = sedeList.get(0);
			articuloSedeService.getSede(0L, sedeEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una sede no asociado a un articulo.
	 *
	 */
	@Test
	void testGetNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, ()->{
			ArticuloEntity newArticulo = factory.manufacturePojo(ArticuloEntity.class);
			entityManager.persist(newArticulo);

			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			articuloSedeService.getSede(newArticulo.getId(), sede.getId());
		});
	}

	/**
	 * Prueba para actualizar las sedes de un articulo.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceSedes() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			articulo.getSedes().add(entity);
			nuevaLista.add(entity);
		}
		articuloSedeService.replaceSedes(articulo.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = articuloSedeService.getSedes(articulo.getId());
		for (SedeEntity sede : nuevaLista) {
			assertTrue(sedeEntities.contains(sede));
		}
	}
	
	/**
	 * Prueba para actualizar las sedes de un articulo.
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
		articuloSedeService.replaceSedes(articulo.getId(), nuevaLista);
		
		List<SedeEntity> authorEntities = articuloSedeService.getSedes(articulo.getId());
		for (SedeEntity sede : nuevaLista) {
			assertTrue(authorEntities.contains(sede));
		}
	}
	
	
	/**
	 * Prueba para actualizar las sedes de un articulo que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidArticulo(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getArticulos().add(articulo);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			articuloSedeService.replaceSedes(0L, nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar las sedes que no existen de un articulo.
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
			articuloSedeService.replaceSedes(articulo.getId(), nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar una sede de un articulo que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getArticulos().add(articulo);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			articuloSedeService.replaceSedes(0L, nuevaLista);
		});
	}

	/**
	 * Prueba desasociar un autor con un libro.
	 *
	 */
	@Test
	void testRemoveSede() throws EntityNotFoundException {
		for (SedeEntity sede : sedeList) {
			articuloSedeService.removeSede(articulo.getId(),sede.getId());;
		}
		assertTrue(articuloSedeService.getSedes(articulo.getId()).isEmpty());
	}
	
	/**
	 * Prueba desasociar una ssede que no existe con un articulo.
	 *
	 */
	@Test
	void testRemoveInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			articuloSedeService.removeSede(articulo.getId(),0L);;
		});
	}
	
	/**
	 * Prueba desasociar una sede con un articulo que no existe.
	 *
	 */
	@Test
	void testRemoveAuthorInvalidArticulo(){
		assertThrows(EntityNotFoundException.class, ()->{
			articuloSedeService.removeSede(0L, sedeList.get(0).getId());
		});
	}
}
