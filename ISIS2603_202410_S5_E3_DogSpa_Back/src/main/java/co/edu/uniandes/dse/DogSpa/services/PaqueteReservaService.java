package co.edu.uniandes.dse.DogSpa.services;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Calse que asocia una reserva existente a un paquete.
 *
 * @author Santiago Gómez
 */

 @Slf4j
 @Service

public class PaqueteReservaService {
    @Autowired
	private PaqueteRepository paqueteRepository;

	@Autowired
	private ReservaRepository reservaRepository;

    /**
	 * Asocia una reserva existente a un Paquete
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param reservaId Identificador de la instancia de Reserva
	 * @return Instancia de ReservaEntity que fue asociada a Paquete
	 */
	@Transactional
	public ReservaEntity addReserva(Long paqueteId, Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una reserva al paquete id = {0}", paqueteId);
		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getPaquetesDeLaReserva().add(reservaEntity.get());
		log.info("Termina proceso de asociarle una reserva al paquete con id = {0}", paqueteId);
		return reservaEntity.get();
	}

	/**
	 * Obtiene una instancia de ReservaEntity asociada a una instancia de PaqueteEntity
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param reservaId Identificador de la instancia de Reserva
	 * @return La entidad de la Reserva asociada al Paquete
	 */
	@Transactional
	public ReservaEntity getReserva(Long paqueteId, Long reservaId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una reserva del paquete con id = {0}", paqueteId);
		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException("El paquete no fue encontrado");

		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException("La reserva no fue encontrada");

		log.info("Termina proceso de consultar una reserva del libro con id = {0}", paqueteId);
		if (!paqueteEntity.get().getPaquetesDeLaReserva().contains(reservaEntity.get()))
			throw new IllegalOperationException("La reserva no está asociada con el paquete");
		
		return reservaEntity.get();
	}

    /**
	 * Obtiene una colección de instancias de ReservaEntity asociadas a una instancia
	 * de Paquete
	 *
	 * @param paqueteId Identificador de la instancia de Paquete
	 * @return Colección de instancias de ReservaEntity asociadas a la instancia de
	 *         Paquete
	 */
	@Transactional
	public List<ReservaEntity> getPaquetesDeLaReserva(Long paqueteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las reservas del paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todas las reservas del paquete con id = {0}", paqueteId);
		return paqueteEntity.get().getPaquetesDeLaReserva();
	}

    @Transactional
	/**
	 * Desasocia una Reserva existente de un Paquete existente
	 *
	 * @param paqueteId   Identificador de la instancia de Paquete
	 * @param reservaId Identificador de la instancia de Reserva
	 */
	public void removeReserva(Long paqueteId, Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una reserva del paquete con id = {0}", paqueteId);
		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

		paqueteEntity.get().getPaquetesDeLaReserva().remove(reservaEntity.get());

		log.info("Termina proceso de borrar una reserva del paquete con id = {0}", paqueteId);
	}

}
