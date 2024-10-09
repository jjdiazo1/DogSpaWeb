/*
MIT License

Copyright (c) 2021 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package co.edu.uniandes.dse.DogSpa.services;

import static org.junit.jupiter.api.Assertions.*;

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
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Paquete
 *
 * @author Santiago Gómez
 */
@DataJpaTest
@Transactional
@Import(PaqueteService.class)

class PaqueteServiceTest {

    @Autowired
	private PaqueteService paqueteService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaqueteEntity> paqueteList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();
    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<ReservaEntity> reservaList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ReservaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PaqueteEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			PaqueteEntity paqueteEntity = factory.manufacturePojo(PaqueteEntity.class);
			entityManager.persist(paqueteEntity);
			paqueteList.add(paqueteEntity);
		}

        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }

        for (int i = 0; i < 3; i++) {
            ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicioEntity);
            servicioList.add(servicioEntity);
        }
	}

    /**
	 * Prueba para crear un Paquete.
	 * @throws IllegalOperationException 
	 */
	@Test
	void testCreatePaquete() throws IllegalOperationException {
		PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
		newEntity.setSedes(sedeList);
        newEntity.setServicios(servicioList);
        newEntity.setPaquetesDeLaReserva(reservaList);
		PaqueteEntity result = paqueteService.createPaquete(newEntity);
        assertNotNull(result);
        PaqueteEntity entity = entityManager.find(PaqueteEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());   
	}

	/**
	 * Prueba para crear un paquete con precio inválido
	 */

	 @Test
	 void testCreatePaqueteWithNoValidPrecio() {
		 assertThrows(IllegalOperationException.class, () -> {
				 PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
				 newEntity.setSedes(sedeList);
				 newEntity.setPrecio(-1);
				 paqueteService.createPaquete(newEntity);
		 });}
		 
	 @Test
	 void testCreatePaqueteWithInvalidSede() {
		 assertThrows(IllegalOperationException.class, () -> {
				 PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
				 newEntity.setPrecio(1000);
				 SedeEntity sedeEntity = new SedeEntity();
				 sedeEntity.setId(0L);
				 List<SedeEntity> sedes = new ArrayList<>();
				 sedes.add(sedeEntity);
				 newEntity.setSedes(sedes);
				 paqueteService.createPaquete(newEntity);
		 });
		 }
	 @Test
	 void testCreatePaqueteWithNullSede() {
			assertThrows(IllegalOperationException.class, () -> {
					 PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
					 newEntity.setPrecio(1000);
					 newEntity.setSedes(null);
					 paqueteService.createPaquete(newEntity);
			 });
			 }

	 @Test
	 void testCreatePaqueteWithInvalidService() {
		 assertThrows(IllegalOperationException.class, () -> {
				 PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
				 newEntity.setPrecio(1000);
				 ServicioEntity servicioEntity = new ServicioEntity();
				 servicioEntity.setId(0L);
				 List<ServicioEntity> servicios = new ArrayList<>();
				 servicios.add(servicioEntity);
				 newEntity.setServicios(servicios);
				 paqueteService.createPaquete(newEntity);
		 });
		 }
	 @Test
	 void testCreatePaqueteWithNullService() {
			assertThrows(IllegalOperationException.class, () -> {
					 PaqueteEntity newEntity = factory.manufacturePojo(PaqueteEntity.class);
					 newEntity.setPrecio(1000);
					 newEntity.setServicios(null);
					 paqueteService.createPaquete(newEntity);
			 });
			 }

	@Test
    void testGetPaquetes() {
        List<PaqueteEntity> list = paqueteService.getEntities();
        assertEquals(paqueteList.size(), list.size());
        for (PaqueteEntity entity : list) {
                boolean found = false;
                for (PaqueteEntity storedEntity : paqueteList) {
                        if (entity.getId().equals(storedEntity.getId())) {
                                found = true;
                        }
                }
                assertTrue(found);
        }
	}

		@Test
		void testGetPaquete() throws EntityNotFoundException {
				PaqueteEntity entity = paqueteList.get(0);
				PaqueteEntity resultEntity = paqueteService.getPaquete(entity.getId());
				assertNotNull(resultEntity);
				assertEquals(entity.getId(), resultEntity.getId());
				assertEquals(entity.getNombre(), resultEntity.getNombre());
				assertEquals(entity.getPrecio(), resultEntity.getPrecio());
		
		}

		@Test
		void testGetInvalidPaquete() {
				assertThrows(EntityNotFoundException.class,()->{
						paqueteService.getPaquete(0L);
				});
		}

		@Test
		void testUpdatePaquete() throws EntityNotFoundException, IllegalOperationException {
				PaqueteEntity entity = paqueteList.get(0);
				PaqueteEntity pojoEntity = factory.manufacturePojo(PaqueteEntity.class);
				pojoEntity.setId(entity.getId());
				paqueteService.updatePaquete(entity.getId(), pojoEntity);
		
				PaqueteEntity resp = entityManager.find(PaqueteEntity.class, entity.getId());
				assertEquals(pojoEntity.getId(), resp.getId());
				assertEquals(pojoEntity.getNombre(), resp.getNombre());
				assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
			   
		}

		@Test
		void testUpdatePaqueteInvalid() {
				assertThrows(EntityNotFoundException.class, () -> {
						PaqueteEntity pojoEntity = factory.manufacturePojo(PaqueteEntity.class);
						pojoEntity.setId(0L);
						paqueteService.updatePaquete(0L, pojoEntity);
				});
		}
		@Test
		void testUpdatePaquetekWithNoValidPrecio() {
				assertThrows(IllegalOperationException.class, () -> {
						PaqueteEntity entity = paqueteList.get(0);
						PaqueteEntity pojoEntity = factory.manufacturePojo(PaqueteEntity.class);
						pojoEntity.setPrecio(-1);;
						pojoEntity.setId(entity.getId());
						paqueteService.updatePaquete(entity.getId(), pojoEntity);
				});
		}
		
		@Test
		void testDeletePaquete() throws EntityNotFoundException, IllegalOperationException {
				PaqueteEntity entity = paqueteList.get(0);
				paqueteService.deletePaquete(entity.getId());
				PaqueteEntity deleted = entityManager.find(PaqueteEntity.class, entity.getId());
				assertNull(deleted);
		}
		
		@Test
		void testDeleteInvalidPaquete() {
				assertThrows(EntityNotFoundException.class, ()->{
						paqueteService.deletePaquete(0L);
				});
		}
		/* 
		@Test
		void testDeletePaqueteWithReservas() {
				assertThrows(IllegalOperationException.class, () -> {
						PaqueteEntity entity = paqueteList.get(0);
						paqueteService.deletePaquete(entity.getId());
				});
		}

		@Test
		void testDeletePaqueteWithSedes() {
				assertThrows(IllegalOperationException.class, () -> {
						PaqueteEntity entity = paqueteList.get(0);
						paqueteService.deletePaquete(entity.getId());
				});
		}
		*/
    
}
