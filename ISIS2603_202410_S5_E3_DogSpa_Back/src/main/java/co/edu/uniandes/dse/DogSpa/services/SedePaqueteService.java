package co.edu.uniandes.dse.DogSpa.services;


import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Laura Lara
 */

 @Slf4j
 @Service

public class SedePaqueteService {
    @Autowired
	private PaqueteRepository paqueteRepository;

	

	@Autowired
	private SedeRepository sedeRepository;
    

/**
	 * Asocia una paquete existente a una sede
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param sedeId Identificador de la instancia de Sede
	 * @return Instancia de PaqueteEntity que fue asociada a la sede
	 */
@Transactional
	public PaqueteEntity addPaquete(Long sedeId, Long paqueteId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un paquete a la sede id = {0}", sedeId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeEntity.get().getPaquetesDeServicios().add(paqueteEntity.get());
		paqueteEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle un paquete a la sede con id = {0}", paqueteId);
		return paqueteEntity.get();
	}


    /**
	 * Obtiene una colección de instancias de PaqueteEntity asociadas a una instancia
	 * de la sede
	 *
	 * @param sedeId Identificador de la instancia de la sede
	 * @return Colección de instancias de paqueteEntity asociadas a la instancia de
	 *         sede
	 */
@Transactional
    public PaqueteEntity getPaquete(Long sedeId,Long paqueteId) throws 
		 EntityNotFoundException, IllegalOperationException {
			log.info("Inicia proceso de consultar un paquete de la sede con id = {0}", sedeId);
			Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
	
			if (paqueteEntity.isEmpty())
				throw new EntityNotFoundException("El paquete no fue encontrado");
	
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException("La sede no fue encontrada");
	
			log.info("Termina proceso de consultar una sede del libro con id = {0}", paqueteId);
			if (!sedeEntity.get().getPaquetesDeServicios().contains(paqueteEntity.get()))
				throw new IllegalOperationException("La sede no esta asociada al artículo");
			
			return paqueteEntity.get();
		}

@Transactional
	public List<PaqueteEntity> getPaqueteEntities(Long sedeId) throws EntityNotFoundException {
			log.info("Inicia proceso de consultar todas los paquetes de la sede con id = {0}", sedeId);
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
			log.info("Finaliza proceso de consultar todas las Paquetes de la sede con id = {0}",sedeId);
			return sedeEntity.get().getPaquetesDeServicios();
		}
 /**
	 * Desasocia un paquete existente de una sede existente
	 *
	 * @param paqueteoId   Identificador de la instancia de Paquete
	 * @param sedeId Identificador de la instancia de Sede
	 */
@Transactional
	public void removePaquete(Long paqueteId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un paquete de la sede con id = {0}", paqueteId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeEntity.get().getPaquetesDeServicios().remove(paqueteEntity.get());

		log.info("Termina proceso de borrar un paquete de la sede con id = {0}", paqueteId);
	}    

@Transactional
/**
 * 
 * @param sedeId 
 * @param list   
 *               
 * @return 
 */
public List<PaqueteEntity> replacePaquetes(Long sedeId, List<PaqueteEntity> list) throws EntityNotFoundException {
	log.info("Inicia proceso de reemplazar paquetes de la sede ", sedeId);
	Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
	if (sedeEntity.isEmpty())
		throw new EntityNotFoundException("El paquete no fue encontrado");

	for (PaqueteEntity paquete : list){
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paquete.getId());
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrada");
	

		if (!sedeEntity.get().getPaquetesDeServicios().contains(paqueteEntity.get()))
			sedeEntity.get().getPaquetesDeServicios().add(paqueteEntity.get());
	}
	log.info("Termina proceso de reemplazar los paquetes de la sede con id = {0}", sedeId);
	return sedeEntity.get().getPaquetesDeServicios();
}





}