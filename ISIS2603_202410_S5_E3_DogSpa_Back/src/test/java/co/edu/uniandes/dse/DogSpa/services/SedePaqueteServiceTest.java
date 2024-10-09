package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(SedePaqueteService.class)
public class SedePaqueteServiceTest {

    @Autowired
    private PaqueteRepository paqueteRepository;


    @Autowired
    private SedePaqueteService sedePaqueteService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PaqueteEntity> paqueteList = new ArrayList<>();
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
            PaqueteEntity paqueteEntity = factory.manufacturePojo(PaqueteEntity.class);
            paqueteEntity.getSedes().add(sede);
            entityManager.persist(paqueteEntity);
            paqueteList.add(paqueteEntity);
        }
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
    }

    /**
     * Prueba para asociar un paquete a una sede.
     *
     */
    @Test
    void testAddPaquete() throws EntityNotFoundException, IllegalOperationException {
        PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
        newPaquete.getSedes().add(sede);
        paqueteRepository.save(newPaquete);

        PaqueteEntity paqueteEntity = sedePaqueteService.addPaquete(sede.getId(), newPaquete.getId());
        assertNotNull(paqueteEntity);

        assertEquals(paqueteEntity.getId(), newPaquete.getId());
        assertEquals(paqueteEntity.getNombre(), newPaquete.getNombre());
        assertEquals(paqueteEntity.getPrecio(), newPaquete.getPrecio());

        assertEquals(paqueteEntity.getId(), newPaquete.getId());
        assertEquals(paqueteEntity.getNombre(), newPaquete.getNombre());
        assertEquals(paqueteEntity.getPrecio(), newPaquete.getPrecio());
    }

    /**
     * Prueba para asociar un paquete a una sede que no existe.
     *
     */
    @Test
    void testAddPaqueteInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            PaqueteEntity newPaquete = factory.manufacturePojo(PaqueteEntity.class);
            newPaquete.getSedes().add(sede);
            paqueteRepository.save(newPaquete);
            sedePaqueteService.addPaquete(newPaquete.getId(), 0L);
        });
    }

    /**
     * Prueba para asociar un paquete que no existe a una sede.
     *
     */
    @Test
    void testAddInvalidPaquete() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedePaqueteService.addPaquete(0L, sede.getId());
        });
    }

    /**
     * Prueba desasociar un paquete con una sede que no existe.
     *
     */
    @Test
    void testRemovePaqueteInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            for (PaqueteEntity paquete : paqueteList) {
                sedePaqueteService.removePaquete(0L, paquete.getId());
            }
        });
    }

    /**
     * Prueba desasociar un paquete que no existe con una sede.
     *
     */
    @Test
    void testRemoveInvalidPaquete() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedePaqueteService.removePaquete(sede.getId(), 0L);
        });
    }
}
