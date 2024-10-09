package co.edu.uniandes.dse.DogSpa.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservaService {
  
  @Autowired()
  private ReservaRepository reservaRepository;

  // Crear una reserva
  @Transactional
  public ReservaEntity createReserva(ReservaEntity reservaEntity) throws IllegalOperationException {
    log.info("Inicia proceso de creacion de una reserva");
    if (reservaEntity.getSede() == null)
			throw new IllegalOperationException("La reserva no puede estar sin sede");

      // Example 2021-12-17 -- yyyy-MM-dd
    if (dateBefore(reservaEntity.getFecha()))
      throw new IllegalOperationException("No se puede ingresar una fecha antes de la actual");

    return reservaRepository.save(reservaEntity);
  }

  // Listas las reservas
  @Transactional
  public List<ReservaEntity> getReservas() {
    log.info("Obteniendo todas las reservas");
    return reservaRepository.findAll();
  }

  // Consultar una reserva
  @Transactional
  public ReservaEntity getReserva(Long reservaID) throws EntityNotFoundException {
    log.info("Inicia el proceso de consultar las reservas de una sede con id = {0}", reservaID);
    Optional <ReservaEntity> reservaEntity = reservaRepository.findById(reservaID);
    
    if (reservaEntity.isEmpty())
      throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);
    
    return reservaEntity.get();
  }

  // Actualizar una reserva
  @Transactional
  public ReservaEntity updateReserva(Long reservaID, ReservaEntity reserva) throws EntityNotFoundException {
    log.info("Inicia el proceso de actualizar una reserva con el id = {0}", reservaID);
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaID);
    if (reservaEntity.isEmpty())
      throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);
    reserva.setId(reservaEntity.get().getId());
    return reservaRepository.save(reserva);
  }

  // Borrar una reserva
  @Transactional
  public void deleteReserva (Long reservaID) throws EntityNotFoundException {
    log.info("Inicia el proceso de borrar una reserva con el id = {0}", reservaID);
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaID);
    if (reservaEntity.isEmpty())
      throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);
    reservaRepository.deleteById(reservaID);
    log.info("Termina el proceso de borrar una reserva con el id = {0}", reservaID);
  }

  

  // Verificar que la fecha no sea antes que la actual
  private boolean dateBefore (LocalDateTime dateInput) {
    LocalDateTime currentDate = LocalDateTime.now();
    return (dateInput.isBefore(currentDate));
  }
}
