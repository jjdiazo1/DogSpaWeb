package co.edu.uniandes.dse.DogSpa.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservaSedeService {
  
  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private SedeRepository sedeRepository;

  // Añadir una sede
  @Transactional
  public SedeEntity addSede(Long sedeId, long reservaId) throws EntityNotFoundException {
    log.info("Inicia proceso de asociarle una sede a la reserva id = {0}", reservaId);
    Optional <SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
    Optional <ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

    if (sedeEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

    if (reservaEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

    sedeEntity.get().getReservas().add(reservaEntity.get());
    log.info("Termina proceso de asociarle un Reserva a la sede con id = {0}", reservaId);
    return sedeEntity.get();
  }

  // Obtener una sede por el ID de la reserva
  @Transactional
  public SedeEntity getSede(long reservaId) throws EntityNotFoundException {
    log.info("Inicia proceso de consultar la sede de la reserva con id = {0}", reservaId);
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
    if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
    return reservaEntity.get().getSede();
  }

  
}
