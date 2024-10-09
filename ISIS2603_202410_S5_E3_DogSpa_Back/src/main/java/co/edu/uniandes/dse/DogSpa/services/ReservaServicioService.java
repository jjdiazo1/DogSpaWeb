package co.edu.uniandes.dse.DogSpa.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.ReservaRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservaServicioService {
  
  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private ServicioRepository servicioRepository;

  @Transactional
  public ServicioEntity addServicio(Long servicioId, Long reservaId) throws EntityNotFoundException {
    log.info("Inicia proceso de asociarle un servicio a la reserva id = {0}", reservaId);

    Optional <ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
    Optional <ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

    if (servicioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

    if (reservaEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

    reservaEntity.get().getServicios().add(servicioEntity.get());
    servicioEntity.get().setServiciosDeReserva(reservaEntity.get());

    log.info("Termina proceso de asociarle un servicio a la reserva id = {0}", reservaId);
    return servicioEntity.get();
  }

  @Transactional
  public List<ServicioEntity> getServicios(long reservaId) throws EntityNotFoundException {
    log.info("Inicia proceso de consultar el servicio de la reserva con id = {0}", reservaId);
    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);

    if (reservaEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

    List<ServicioEntity> servicioEntity = reservaEntity.get().getServicios();

    if (servicioEntity == null)
			throw new EntityNotFoundException("The service was not found");
    return servicioEntity;
  }

  @Transactional
  public ServicioEntity getServicio(long reservaId, long servicioId) throws EntityNotFoundException, IllegalOperationException {
    log.info("Inicia proceso de consultar el servicio de la reserva con id = {0}", reservaId);

    Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
    Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

    if (reservaEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);
    if (servicioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

    if(!reservaEntity.get().getServicios().contains(servicioEntity.get())) 
      throw new IllegalOperationException("El servicio no esta asociado a la reserva");

    return servicioEntity.get();

  }

  // Borrar un servicio
  @Transactional
  public void removeServicio(Long servicioId, Long reservaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un servicio de la reserva con id = {0}", reservaId);

		Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaId);
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

		if (reservaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESERVA_NOT_FOUND);

		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

    reservaEntity.get().getServicios().remove(servicioEntity.get());

		log.info("Termina proceso de borrar un servicio de la reserva con id = {0}", reservaId);
	}
}
