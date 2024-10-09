package co.edu.uniandes.dse.DogSpa.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.repositories.ArticuloRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;

//import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Laura V Lara
 */

@Slf4j
@Service

public class SedeService {
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
    private ArticuloRepository articuloRepository;

	@Transactional
	public SedeEntity createSede(SedeEntity sedeEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación de la sede");
		return sedeRepository.save(sedeEntity);
	}

	@Transactional
	public List<SedeEntity> getSedesOrdenadasPorCiudad() {
    log.info("Inicia proceso de obtener las sedes ordenadas por ciudad");
    List<SedeEntity> sedes = sedeRepository.findAll();
    Collections.sort(sedes, Comparator.comparing(SedeEntity::getCiudad));
    return sedes;
}
	@Transactional
	public List<SedeEntity> getEntities() {
		log.info("Inicia proceso de consultar las sedes");
		return sedeRepository.findAll();
	}

	@Transactional
	public SedeEntity getSede(Long sedeID) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la sede con id = {0}", sedeID);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeID);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Termina proceso de consultar el autor con id = {0}", sedeID);
		return sedeEntity.get();
	}

	@Transactional
	public SedeEntity updateSede(Long sedeId, SedeEntity sede) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el autor con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Termina proceso de actualizar el autor con id = {0}", sedeId);
		sede.setId(sedeId);
		return sedeRepository.save(sede);
	}

	@Transactional
	public void deleteSede(Long sedeID) throws IllegalOperationException, EntityNotFoundException {
		log.info("Inicia proceso de borrar el sede con id = {0}", sedeID);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeID);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeRepository.deleteById(sedeID);
		log.info("Termina proceso de borrar la sede con id = {0}", sedeID);
	}

	
	@Transactional
	public List<SedeEntity> getFiltro( Long articuloId) throws EntityNotFoundException {
		log.info("Inicia proceso de filtrado");

		List<SedeEntity> sedes = sedeRepository.findAll();
		log.info("sedes", sedes);

		List<SedeEntity> respuesta = new ArrayList<>();

	   for (SedeEntity sede : sedes) {

		   for (ArticuloEntity articulo : sede.getArticulos()) {
			   log.info("articulo", articulo);
			   
			   if (articulo.getId() == articuloId){
				   respuesta.add(sede);
				   break;
			   }
		   }	
	   }

	   if (0 == articuloId){
		   respuesta = sedeRepository.findAll();
		   
	   }

	   log.info("Respuesta del filtro: {}", respuesta);
		return respuesta;
	} 

    @Transactional
	public List<SedeEntity> getSedesPorCiudad(String ciudad) {
    log.info("Inicia proceso de obtener sedes por ciudad: {}", ciudad);
    return sedeRepository.findByCiudadIgnoreCase(ciudad);
}
	@Transactional
	public List<SedeEntity> getFiltroPorCiudad(String ciudad) throws EntityNotFoundException {
		log.info("Inicia proceso de filtrado por ciudad");
	
		List<SedeEntity> sedes = sedeRepository.findAll();
		log.info("sedes", sedes);
	
		List<SedeEntity> respuesta = new ArrayList<>();
	
		for (SedeEntity sede : sedes) {
			if (sede.getCiudad().equals(ciudad)){
				respuesta.add(sede);
			}
		}
	
		log.info("Respuesta del filtro!: {}", respuesta);
		return respuesta;
	}
	

}
