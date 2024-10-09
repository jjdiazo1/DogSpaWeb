package co.edu.uniandes.dse.DogSpa.services;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Laura Lara
 */

 @Slf4j
 @Service

public class SedeReservaService {
    @Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private SedeRepository sedeRepository;
    

/**
	 * Asocia una Reserva existente a una sede
	 *
	 * @param reservaId   Identificador de la instancia de Reserva
	 * @param sedeId Identificador de la instancia de Sede
	 * @return Instancia de ReservaEntity que fue asociada a la sede
	 */
@Transactional
	public ReservaEntity addReserva(Long sedeId, Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un Reserva a la sede id = {0}", sedeId);
		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		

			reservaEntity.get().setSede(sedeEntity.get());

	


			sedeEntity.get().getReservas().add(reservaEntity.get());
		
		
			log.info("Termina proceso de asociarle un Reserva a la sede con id = {0}", reservaId);
			return reservaEntity.get();
		}
	
	

    /**
	 * Obtiene una colección de instancias de reservaEntity asociadas a una instancia
	 * de la sede
	 *
	 * @param sedeId Identificador de la instancia de la sede
	 * @return Colección de instancias de reservaEntity asociadas a la instancia de
	 *         sede
	 */
@Transactional
    public List<ReservaEntity> getReservaEntities(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas los Reservas de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);
		log.info("Finaliza proceso de consultar todas las Reservas de la sede con id = {0}",sedeId);
		return sedeEntity.get().getReservas();
	}
 /**
	 * Desasocia un Reserva existente de una sede existente
	 *
	 * @param ReservaId   Identificador de la instancia de Reserva
	 * @param sedeId Identificador de la instancia de Sede
	 */
@Transactional
	public void removeReserva(Long reservaId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un Reserva de la sede con id = {0}", reservaId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeEntity.get().getReservas().remove(reservaEntity.get());

		log.info("Termina proceso de borrar un Reserva de la sede con id = {0}", reservaId);
	}    

@Transactional
	/**
	 * 
	 * @param sedeId 
	 * @param list   
	 *               
	 * @return 
	 */
	public List<ReservaEntity> replaceReservas(Long sedeId, List<ReservaEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar reservas de la sede ", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("El reserva no fue encontrado");
	
		for (ReservaEntity reserva : list){
			Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reserva.getId());
			if (reservaEntity.isEmpty())
				throw new EntityNotFoundException("El reserva no fue encontrada");
		
	
			if (!sedeEntity.get().getReservas().contains(reservaEntity.get()))
				sedeEntity.get().getReservas().add(reservaEntity.get());
		}
		log.info("Termina proceso de reemplazar los reservas de la sede con id = {0}", sedeId);
		return sedeEntity.get().getReservas();
	}
@Transactional
		public ReservaEntity getReserva(Long sedeId,Long reservaId) throws 
			 EntityNotFoundException, IllegalOperationException {
				log.info("Inicia proceso de consultar un reserva de la sede con id = {0}", sedeId);
				Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
				Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		
				if (reservaEntity.isEmpty())
					throw new EntityNotFoundException("El reserva no fue encontrado");
		
				if (sedeEntity.isEmpty())
					throw new EntityNotFoundException("La sede no fue encontrada");
		
				log.info("Termina proceso de consultar una sede del libro con id = {0}", reservaId);
				if (!sedeEntity.get().getReservas().contains(reservaEntity.get()))
					throw new IllegalOperationException("La sede no esta asociada al artículo");
				
				return reservaEntity.get();
			}
	
}
    

