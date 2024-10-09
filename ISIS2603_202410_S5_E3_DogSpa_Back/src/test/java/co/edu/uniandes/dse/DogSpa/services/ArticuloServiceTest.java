package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
@Import(ArticuloService.class)

public class ArticuloServiceTest {
    
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ArticuloEntity> articuloList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();

    /**
         * Configuración inicial de la prueba.
         */
    @BeforeEach
    void setUp() {
                clearData();
                insertData();
        }

    private void insertData() {

       for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }
       
       
        for (int i = 0; i < 3; i++) {
            ArticuloEntity articuloEntity = factory.manufacturePojo(ArticuloEntity.class);
            articuloEntity.setSedes(sedeList);
            entityManager.persist(articuloEntity);
            articuloList.add(articuloEntity);
        }
    }

    private void clearData() {
       
        entityManager.getEntityManager().createQuery("delete from ArticuloEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
    }

    
    /**
	 * Prueba para crear un Articulo
	 */
    @Test
    void testCreateArticulo() throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity newEntity = factory.manufacturePojo(ArticuloEntity.class);
        newEntity.setSedes(sedeList);
        newEntity.setPrecio(1234);
        ArticuloEntity result = articuloService.createArticulo(newEntity);
        assertNotNull(result);
        ArticuloEntity entity = entityManager.find(ArticuloEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());  
        assertEquals(newEntity.getImagen(), entity.getImagen());     
    }

    /**
	 * Prueba para crear un articulo con precio inválido
	 */

    @Test
    void testCreateArticuloWithNoValidPrecio() {
        assertThrows(IllegalOperationException.class, () -> {
                ArticuloEntity newEntity = factory.manufacturePojo(ArticuloEntity.class);
                newEntity.setSedes(sedeList);
                newEntity.setPrecio(-1);
                articuloService.createArticulo(newEntity);
        });}
        
    @Test
    void testCreateArticuloWithInvalidSede() {
        assertThrows(IllegalOperationException.class, () -> {
                ArticuloEntity newEntity = factory.manufacturePojo(ArticuloEntity.class);
                newEntity.setPrecio(1000);
                SedeEntity sedeEntity = new SedeEntity();
                sedeEntity.setId(0L);
                List<SedeEntity> sedes = new ArrayList<>();
                sedes.add(sedeEntity);
                newEntity.setSedes(sedes);
                articuloService.createArticulo(newEntity);
        });
        }
    
    @Test
    void testCreateArticuloWithNullSede() {
        assertThrows(IllegalOperationException.class, () -> {
                ArticuloEntity newEntity = factory.manufacturePojo(ArticuloEntity.class);
                newEntity.setPrecio(1000);
                newEntity.setSedes(null);
                articuloService.createArticulo(newEntity);
        });
        }

    @Test
    void testGetArticulos() {
        List<ArticuloEntity> list = articuloService.getArticulos();
        assertEquals(articuloList.size(), list.size());
        for (ArticuloEntity entity : list) {
                boolean found = false;
                for (ArticuloEntity storedEntity : articuloList) {
                        if (entity.getId().equals(storedEntity.getId())) {
                                found = true;
                        }
                }
                assertTrue(found);
        }
}

@Test
void testGetBook() throws EntityNotFoundException {
        ArticuloEntity entity = articuloList.get(0);
        ArticuloEntity resultEntity = articuloService.getArticulo(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getImagen(), resultEntity.getImagen());

}

@Test
void testGetInvalidArticulo() {
        assertThrows(EntityNotFoundException.class,()->{
                articuloService.getArticulo(0L);
        });
}

@Test
void testUpdateBook() throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity entity = articuloList.get(0);
        ArticuloEntity pojoEntity = factory.manufacturePojo(ArticuloEntity.class);
        pojoEntity.setId(entity.getId());
        articuloService.updateArticulo(entity.getId(), pojoEntity);

        ArticuloEntity resp = entityManager.find(ArticuloEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getImagen(), resp.getImagen());
        
}

@Test
void testUpdateArticuloInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
                ArticuloEntity pojoEntity = factory.manufacturePojo(ArticuloEntity.class);
                pojoEntity.setId(0L);
                articuloService.updateArticulo(0L, pojoEntity);
        });
}

@Test
void testUpdateArticulokWithNoValidPrecio() {
        assertThrows(IllegalOperationException.class, () -> {
                ArticuloEntity entity = articuloList.get(0);
                ArticuloEntity pojoEntity = factory.manufacturePojo(ArticuloEntity.class);
                pojoEntity.setPrecio(-1);;
                pojoEntity.setId(entity.getId());
                articuloService.updateArticulo(entity.getId(), pojoEntity);
        });
}

@Test
void testDeleteArticulo() throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity entity = articuloList.get(0);
        entity.getSedes().clear();
        articuloService.deleteArticulo(entity.getId());
        ArticuloEntity deleted = entityManager.find(ArticuloEntity.class, entity.getId());
        assertNull(deleted);
}

@Test
void testDeleteInvalidArticulo() {
        assertThrows(EntityNotFoundException.class, ()->{
                articuloService.deleteArticulo(0L);
        });
}

@Test
void testDeleteArticuloWithSedes() {
        assertThrows(IllegalOperationException.class, () -> {
                ArticuloEntity entity = articuloList.get(0);
                articuloService.deleteArticulo(entity.getId());
        });
}

}