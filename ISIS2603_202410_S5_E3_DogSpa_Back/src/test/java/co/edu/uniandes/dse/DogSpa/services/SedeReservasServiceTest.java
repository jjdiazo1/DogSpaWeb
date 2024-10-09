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

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(SedeReservaService.class)

public class SedeReservasServiceTest {

    @Autowired
    private ReservaRepository reservaRepository;


    @Autowired
    private SedeReservaService sedeReservaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ReservaEntity> reservaList = new ArrayList<>();
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
            ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
            reservaEntity.getSede();
            entityManager.persist(reservaEntity);
            reservaList.add(reservaEntity);
        }
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
    }

    /**
     * Prueba para asociar una reserva a una sede.
     *
     */
    @Test
    void testAddReserva() throws EntityNotFoundException {
        ReservaEntity newReserva = factory.manufacturePojo(ReservaEntity.class);
        newReserva.getSede();
        reservaRepository.save(newReserva);

        ReservaEntity reservaEntity = sedeReservaService.addReserva(sede.getId(), newReserva.getId());
        assertNotNull(reservaEntity);

        assertEquals(reservaEntity.getId(), newReserva.getId());
        assertEquals(reservaEntity.getNombreMascota(), newReserva.getNombreMascota());
        // Agrega más comparaciones según la estructura de tu entidad ReservaEntity
    }

    /**
     * Prueba para asociar una reserva a una sede que no existe.
     *
     */
    @Test
    void testAddReservaInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            ReservaEntity newReserva = factory.manufacturePojo(ReservaEntity.class);
            newReserva.getSede();
            reservaRepository.save(newReserva);
            sedeReservaService.addReserva(newReserva.getId(), 0L);
        });
    }

    /**
     * Prueba para asociar una reserva que no existe a una sede.
     *
     */
    @Test
    void testAddInvalidReserva() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeReservaService.addReserva(0L, sede.getId());
        });
    }

    /**
     * Prueba desasociar una reserva con una sede que no existe.
     *
     */
    @Test
    void testRemoveReservaInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            for (ReservaEntity reserva : reservaList) {
                sedeReservaService.removeReserva(0L, reserva.getId());
            }
        });
    }

    /**
     * Prueba desasociar una reserva que no existe con una sede.
     *
     */
    @Test
    void testRemoveInvalidReserva() {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeReservaService.removeReserva(sede.getId(), 0L);
        });
    }
}
