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
 * Clase que asocia una sede existente a un paquete.
 *
 * @author Santiago Gómez
 */

 @Slf4j
 @Service

public class PaqueteSedeService {
    @Autowired
	private PaqueteRepository paqueteRepository;

	@Autowired
	private SedeRepository sedeRepository;

    /**
	 * Asocia una sede existente a un Paquete
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param sedeId Identificador de la instancia de Sede
	 * @return Instancia de SedeEntity que fue asociada a Paquete
	 */
	@Transactional
	public SedeEntity addSede(Long paqueteId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una sede al paquete id = {0}", paqueteId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle una sede al paquete con id = {0}", paqueteId);
		return sedeEntity.get();
	}

    /**
	 * Obtiene una colección de instancias de SedeEntity asociadas a una instancia
	 * de Paquete
	 *
	 * @param paqueteId Identificador de la instancia de Paquete
	 * @return Colección de instancias de SedeEntity asociadas a la instancia de
	 *         Paquete
	 */
	@Transactional
	public List<SedeEntity> getSedeEntities(Long paqueteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las sedes del paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todas las sedes del paquete con id = {0}", paqueteId);
		return paqueteEntity.get().getSedes();
	}

	/**
	 * Obtiene una instancia de SedeEntity asociada a una instancia de PaqueteEntity
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param sedeId Identificador de la instancia de Sede
	 * @return La entidad de la Sede asociada al Paquete
	 */
	@Transactional
	public SedeEntity getSede(Long paqueteId, Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una sede del paquete con id = {0}", paqueteId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		log.info("Termina proceso de consultar una sede del libro con id = {0}", paqueteId);
		if (!paqueteEntity.get().getSedes().contains(sedeEntity.get()))
			throw new IllegalOperationException("La sede no está asociada con el paquete");
		
		return sedeEntity.get();
	}

	@Transactional
	/**
	 * Remplaza las instancias de sede asociadas a una instancia de Paquete
	 *
	 * @param paqueteId Identificador de la instancia de Paquete
	 * @param list    Colección de instancias de SedeEntity a asociar a instancia
	 *                de Paquete
	 * @return Nueva colección de SedeEntity asociada a la instancia de Paquete
	 */
	public List<SedeEntity> replaceSedes(Long paqueteId, List<SedeEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las sedes del paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrado");

		for (SedeEntity sede : list) {
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException("La sede no fue encontrada");

			if (!paqueteEntity.get().getSedes().contains(sedeEntity.get()))
				paqueteEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Termina proceso de reemplazar las sedes del paquete con id = {0}", paqueteId);
		return paqueteEntity.get().getSedes();
	}

    @Transactional
	/**
	 * Desasocia una Sede existente de un Paquete existente
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param sedeId Identificador de la instancia de Sede
	 */
	public void removeSede(Long paqueteId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una sede del paquete con id = {0}", paqueteId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getSedes().remove(sedeEntity.get());

		log.info("Termina proceso de borrar una sede del paquete con id = {0}", paqueteId);
	}

}
