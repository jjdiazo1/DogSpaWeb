package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.DogSpa.entities.RestriccionEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ServicioService.class)

public class ServicioServiceTest {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<RestriccionEntity> restriccionList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from RestriccionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            RestriccionEntity restriccionEntity = factory.manufacturePojo(RestriccionEntity.class);
            entityManager.persist(restriccionEntity);
            restriccionList.add(restriccionEntity);
        }

        for (int i = 0; i < 3; i++) {
            ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
            servicioEntity.setRestricciones(restriccionList);
            entityManager.persist(servicioEntity);
            servicioList.add(servicioEntity);
        }
    }

    @Test
    void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {   
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setRestricciones(restriccionList);
        ServicioEntity result =  servicioService.createServicio(newEntity);
        assertNotNull(result);
        ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getServiciosDeReserva(), entity.getServiciosDeReserva());
        assertEquals(newEntity.getPaquetes(), entity.getPaquetes());
        assertEquals(newEntity.getSedes(), entity.getSedes());
        assertEquals(newEntity.getRestricciones(), entity.getRestricciones());
    }

    @Test
    void testDeleteServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(1);
        servicioService.deleteServicio(entity.getId());
        ServicioEntity deleted = entityManager.find(ServicioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidServicio() {
        assertThrows(EntityNotFoundException.class, ()->{
            servicioService.deleteServicio(0L);
        });

    }
}
    

