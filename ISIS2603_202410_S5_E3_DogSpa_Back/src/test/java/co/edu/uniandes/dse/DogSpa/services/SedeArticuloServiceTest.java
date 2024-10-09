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
@Import({SedeArticuloService.class, ArticuloService.class})

public class SedeArticuloServiceTest {
    
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private SedeArticuloService sedeArticuloService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ArticuloEntity> articuloList = new ArrayList<>();
    private SedeEntity sede = new SedeEntity();

    /**
         * Configuración inicial de la prueba.
         */
    @BeforeEach
    void setUp() {
                clearData();
                insertData();
        }

    private void insertData() {

       sede = factory.manufacturePojo(SedeEntity.class);
       entityManager.persist(sede);
       
        for (int i = 0; i < 3; i++) {
            ArticuloEntity articuloEntity = factory.manufacturePojo(ArticuloEntity.class);
            articuloEntity.getSedes().add(sede);
            entityManager.persist(articuloEntity);
            articuloList.add(articuloEntity);
            sede.getArticulos().add(articuloEntity);
        }
    }

    private void clearData() {
       
        entityManager.getEntityManager().createQuery("delete from ArticuloEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
    }

    /**
     * Prueba para asociar un articulo a una sede.
     *
     */
    @Test
    void testAddArticulo() throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity newArticulo = factory.manufacturePojo(ArticuloEntity.class);
		entityManager.persist(newArticulo);
		
		SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);
		
        newArticulo.getSedes().add(sede);

		sedeArticuloService.addArticulo(newArticulo.getId(),sede.getId());
		
		ArticuloEntity LastArticulo = sedeArticuloService.getArticulo(newArticulo.getId(),sede.getId());
		
    
        assertEquals(LastArticulo.getId(), newArticulo.getId());
        assertEquals(LastArticulo.getNombre(), newArticulo.getNombre());
        assertEquals(LastArticulo.getDescripcion(), newArticulo.getDescripcion());
        assertEquals(LastArticulo.getPrecio(), newArticulo.getPrecio());
        assertEquals(LastArticulo.getImagen(), newArticulo.getImagen());

    }

    /**
     * Prueba para asociar un articulo a una asede que no existe.
     *
     */

    @Test
    void testAddSedeInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            ArticuloEntity newArticulo = factory.manufacturePojo(ArticuloEntity.class);
            newArticulo.getSedes().add(sede);
            articuloService.createArticulo(newArticulo);
            sedeArticuloService.addArticulo(newArticulo.getId(),0L);
        });
    }

    /**
     * Prueba para asociar unarticulo que no existe a una sede.
     *
     */
    @Test
    void testAddInvalidArticulo() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeArticuloService.addArticulo(0L, sede.getId());
        });
    }

    /**
     * Prueba para consultar la lista de articulos de una sede.
     */
    @Test
    void testGetArticulos() throws EntityNotFoundException {
        List<ArticuloEntity> articuloEntities = sedeArticuloService.getArticulos(sede.getId());

        assertEquals(articuloList.size(), articuloEntities.size());

        for (int i = 0; i < articuloList.size(); i++) {
            assertTrue(articuloEntities.contains(articuloList.get(i)));
        }
    }

    /**
     * Prueba para consultar la lista de articulos de una sede que no existe.
     */
    @Test
    void testGetSedesInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeArticuloService.getArticulos(0L);
        });
    }

    /**
     * Prueba para consultar un articulo de una sede.
     *
     * @throws throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testGetArticulo() throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity articuloEntity = articuloList.get(0);
        ArticuloEntity articulo = sedeArticuloService.getArticulo(articuloEntity.getId(),sede.getId());
        assertNotNull(articulo);

        assertEquals(articuloEntity.getId(), articulo.getId());
        assertEquals(articuloEntity.getNombre(), articulo.getNombre());
        assertEquals(articuloEntity.getDescripcion(), articulo.getDescripcion());
        assertEquals(articuloEntity.getPrecio(), articulo.getPrecio());
        assertEquals(articuloEntity.getImagen(), articulo.getImagen());
        
    
    }

    /**
     * Prueba para consultar un articulo de una sede que no existe.
     *
     * @throws throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testGetArticuloInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            ArticuloEntity articuloEntity = articuloList.get(0);
            sedeArticuloService.getArticulo(articuloEntity.getId(),0L);
        });
    }

    /**
     * Prueba para consultar un articulo que no existe de una sede.
     *
     * @throws throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testGetInvalidArticulo() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeArticuloService.getArticulo(0L,sede.getId());
        });
    }

    /**
     * Prueba para consultar un articulo que no está asociado a una sede.
     *
     * @throws throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testGetArticuloNotAssociatedSede() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);

            ArticuloEntity articuloEntity = factory.manufacturePojo(ArticuloEntity.class);
            entityManager.persist(articuloEntity);

            sedeArticuloService.getArticulo(articuloEntity.getId(),sedeEntity.getId());
        });
    }

    /**
     * Prueba para actualizar los articulos de una sede.
     *
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testReplaceArticuloss() throws EntityNotFoundException, IllegalOperationException {
        List<ArticuloEntity> nuevaLista = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ArticuloEntity entity = factory.manufacturePojo(ArticuloEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }
        
        sedeArticuloService.replaceArticulos(sede.getId(), nuevaLista);
        
        List<ArticuloEntity> articuloEntities = entityManager.find(SedeEntity.class, sede.getId()).getArticulos();
        for (ArticuloEntity item : nuevaLista) {
            assertTrue(articuloEntities.contains(item));
        }
    }
    
    /**
     * Prueba para actualizar los articulos de una sede que no existe.
     *
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testReplaceArticulosInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ArticuloEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ArticuloEntity entity = factory.manufacturePojo(ArticuloEntity.class);
                articuloService.createArticulo(entity);
                nuevaLista.add(entity);
            }
            sedeArticuloService.replaceArticulos(0L, nuevaLista);
        });
    }

    /**
     * Prueba para actualizar los articulos que no existen de una sede.
     *
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @Test
    void testReplaceInvalidSedes() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ArticuloEntity> nuevaLista = new ArrayList<>();
            ArticuloEntity entity = factory.manufacturePojo(ArticuloEntity.class);
            entity.setId(0L);
            nuevaLista.add(entity);
            sedeArticuloService.replaceArticulos(sede.getId(), nuevaLista);
        });
    }

    /**
     * Prueba desasociar un articulo con una sede.
     *
     */
    @Test
    void testRemoveArticulo() throws EntityNotFoundException {
        for (ArticuloEntity articulo : articuloList) {
            sedeArticuloService.removeArticulo(sede.getId(),articulo.getId());;
        }
        assertTrue(sedeArticuloService.getArticulos(sede.getId()).isEmpty());
    }

    /**
     * Prueba desasociar un articulo con una sede que no existe.
     *
     */
    @Test
    void testRemoveSedeInvalidAuthor() {
        assertThrows(EntityNotFoundException.class, () -> {
            for (ArticuloEntity articulo : articuloList) {
                sedeArticuloService.removeArticulo(0L, articulo.getId());
            }
        });
    }

    /**
     * Prueba desasociar un articulo que no existe con una sede.
     *
     */
    @Test
    void testRemoveInvalidArticulo() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeArticuloService.removeArticulo(sede.getId(), 0L);
        });
    }

}