package co.edu.uniandes.dse.DogSpa.services;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Calse que asocia una reserva existente a un paquete.
 *
 * @author Santiago Gómez
 */

 @Slf4j
 @Service

public class PaqueteServicioService {
    @Autowired
	private PaqueteRepository paqueteRepository;

	@Autowired
	private ServicioRepository servicioRepository;

    /**
	 * Asocia una reserva existente a un Paquete
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param servicioId Identificador de la instancia de Servicio
	 * @return Instancia de ServicioEntity que fue asociada a Paquete
	 */
	@Transactional
	public ServicioEntity addServicio(Long paqueteId, Long servicioId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un servicio al paquete id = {0}", paqueteId);
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getServicios().add(servicioEntity.get());
		log.info("Termina proceso de asociarle un servicio al paquete con id = {0}", paqueteId);
		return servicioEntity.get();
	}

    /**
	 * Obtiene una colección de instancias de ServicioEntity asociadas a una instancia
	 * de Paquete
	 *
	 * @param paqueteId Identificador de la instancia de Paquete
	 * @return Colección de instancias de ServicioEntity asociadas a la instancia de
	 *         Paquete
	 */
	@Transactional
	public List<ServicioEntity> getServicios(Long paqueteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los servicios del paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todos los servicios del paquete con id = {0}", paqueteId);
		return paqueteEntity.get().getServicios();
	}

	/**
	 * Obtiene una instancia de ServicioEntity asociada a una instancia de PaqueteEntity
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param servicioId Identificador de la instancia de Servicio
	 * @return La entidad de la Servico asociada al Paquete
	 */
	@Transactional
	public ServicioEntity getServicio(Long paqueteId, Long servicioId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar un servicio del paquete con id = {0}", paqueteId);
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrado");

		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException("El servicio no fue encontrado");

		log.info("Termina proceso de consultar un servicio del paquete con id = {0}", paqueteId);
		if (!paqueteEntity.get().getServicios().contains(servicioEntity.get()))
			throw new IllegalOperationException("El servicio no está asociado con el paquete");
		
		return servicioEntity.get();
	}

	@Transactional
	/**
	 * Remplaza las instancias de servicio asociadas a una instancia de Paquete
	 *
	 * @param paqueteId Identificador de la instancia de Paquete
	 * @param list    Colección de instancias de ServicioEntity a asociar a instancia
	 *                de Paquete
	 * @return Nueva colección de ServicioEntity asociada a la instancia de Paquete
	 */
	public List<ServicioEntity> replaceServicios(Long paqueteId, List<ServicioEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los servicios del paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrado");

		for (ServicioEntity servicio : list) {
			Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicio.getId());
			if (servicioEntity.isEmpty())
				throw new EntityNotFoundException("El servicio no fue encontrado");

			if (!paqueteEntity.get().getServicios().contains(servicioEntity.get()))
				paqueteEntity.get().getServicios().add(servicioEntity.get());
		}
		log.info("Termina proceso de reemplazar los servicios del paquete con id = {0}", paqueteId);
		return paqueteEntity.get().getServicios();
	}

    @Transactional
	/**
	 * Desasocia un Servicio existente de un Paquete existente
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param servicioId Identificador de la instancia de Servicio
	 */
	public void removeServicio(Long paqueteId, Long servicioId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un servicio del paquete con id = {0}", paqueteId);
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getServicios().remove(servicioEntity.get());

		log.info("Termina proceso de borrar un servicio del paquete con id = {0}", paqueteId);
	}

}
