package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;


import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(SedeService.class)

public class SedeServiceTest {

    @Autowired
    private SedeService sedeService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<SedeEntity> sedeList = new ArrayList<>();
    private List<ReservaEntity> reservaList = new ArrayList<>();
    private List<PaqueteEntity> paqueteList = new ArrayList<>();
    private List<ArticuloEntity> articuloList = new ArrayList<>();


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
            entityManager.getEntityManager().createQuery("delete from SedeEntity");
            entityManager.getEntityManager().createQuery("delete from ArticuloEntity");
            entityManager.getEntityManager().createQuery("delete from PaqueteEntity");
            entityManager.getEntityManager().createQuery("delete from ReservaEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
            entityManager.persist(reservaEntity);
            reservaList.add(reservaEntity);
        }

        for (int i = 0; i < 3; i++) {
            PaqueteEntity paqueteEntity = factory.manufacturePojo(PaqueteEntity.class);
            entityManager.persist(paqueteEntity);
            paqueteList.add(paqueteEntity);
        }

        for (int i = 0; i < 3; i++) {
            ArticuloEntity restriccionEntity = factory.manufacturePojo(ArticuloEntity.class);
            entityManager.persist(restriccionEntity);
            articuloList.add(restriccionEntity);
        }

        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            sedeEntity.getReservas();
            sedeEntity.setPaquetesDeServicios(paqueteList);
            sedeEntity.setArticulos(articuloList);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }}

    @Test
    void testCreateSede() throws EntityNotFoundException, IllegalOperationException {   
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setArticulos(articuloList);
        newEntity.setReservas(reservaList);
        newEntity.setPaquetesDeServicios(paqueteList);
        SedeEntity result =  sedeService.createSede(newEntity);
        assertNotNull(result);
        SedeEntity entity = entityManager.find(SedeEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getArticulos(), entity.getArticulos());
        assertEquals(newEntity.getReservas(), entity.getReservas());
        assertEquals(newEntity.getPaquetesDeServicios(), entity.getPaquetesDeServicios());}
    
    @Test
    void testDeleteBook() throws EntityNotFoundException, IllegalOperationException {
                SedeEntity entity = sedeList.get(1);
                sedeService.deleteSede(entity.getId());
                SedeEntity deleted = entityManager.find(SedeEntity.class, entity.getId());
                assertNull(deleted);
        }

    @Test
    void testDeleteInvalidBook() {
            assertThrows(EntityNotFoundException.class, ()->{
                        sedeService.deleteSede(0L);
                });
        }
    
    }

    


    
