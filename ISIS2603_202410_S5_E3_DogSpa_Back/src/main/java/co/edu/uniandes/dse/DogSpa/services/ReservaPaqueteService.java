package co.edu.uniandes.dse.DogSpa.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservaPaqueteService {
  
  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private PaqueteRepository paqueteRepository;

  // Añadir un paquete
  @Transactional
  public PaqueteEntity addPaquete(long paqueteId, long reservaId) throws EntityNotFoundException {
    log.info("Inicia proceso de asociarle un paquete a la reserva id = {0}", reservaId);

    Optional <PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
    Optional <ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

    if (paqueteEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

    if (reservaEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

    reservaEntity.get().getPaquetes().add(paqueteEntity.get());
    paqueteEntity.get().getPaquetesDeLaReserva().add(reservaEntity.get());

    log.info("Termina proceso de asociarle un Reserva a un paquete con id = {0}", reservaId);
    return paqueteEntity.get();
  }

  @Transactional
  public List<PaqueteEntity> addPaquetes(Long reservaId, List<PaqueteEntity> paquetes) throws EntityNotFoundException {
    log.info("Inicia el proceso de reemplazar los paquetes asociados a la reserva con id = {0}", reservaId);
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
    if (reservaEntity.isEmpty())
      throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

      for (PaqueteEntity paquete : paquetes) {
        Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paquete.getId());
        if (paqueteEntity.isEmpty())
          throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
        if (!paqueteEntity.get().getPaquetesDeLaReserva().contains(reservaEntity.get()))
          paqueteEntity.get().getPaquetesDeLaReserva().add(reservaEntity.get());
      }
      log.info("Finaliza proceso de reemplazar los paquetes asociados al author con id = {0}", reservaId);
      reservaEntity.get().setPaquetes(paquetes);
      return reservaEntity.get().getPaquetes();
  }

  // Encontrar los paquetes por el ID de la reserva
  @Transactional
  public List<PaqueteEntity> getPaquetes(long reservaId) throws EntityNotFoundException {
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
    if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
    return reservaEntity.get().getPaquetes();
  }

  // Encontrar un paquete por el ID de reserva
  @Transactional
  public PaqueteEntity getPaquete(long paqueteId, long reservaId) throws EntityNotFoundException, IllegalOperationException {
    Optional <PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
    Optional <ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

    if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
    if (reservaEntity.isEmpty())
      throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

    // Verificamos si estan asociados
    if(!paqueteEntity.get().getPaquetesDeLaReserva().contains(reservaEntity.get()))
      throw new IllegalOperationException("El paquete no esta asociado a la reserva");

    return paqueteEntity.get();
  }

  // Encontrar los paquetes de una reserva
  @Transactional
	public List<PaqueteEntity> getReservasDelPaquete(Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas los paquetes de la reserva con id = {0}", reservaId);

		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		log.info("Finaliza proceso de consultar todas los paquetes de la reserva con id = {0}", reservaId);

		return reservaEntity.get().getPaquetes();
	}

  // Borrar un paquete
  @Transactional
  public void removePaquete(Long paqueteId, Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un paquete de la reserva con id = {0}", reservaId);

		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);

		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

    reservaEntity.get().getPaquetes().remove(paqueteEntity.get());

		log.info("Termina proceso de borrar un paquete de la reserva con id = {0}", reservaId);
	}
}
