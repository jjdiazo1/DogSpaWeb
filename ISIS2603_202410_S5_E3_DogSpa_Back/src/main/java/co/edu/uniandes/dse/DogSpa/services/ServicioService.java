package co.edu.uniandes.dse.DogSpa.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.RestriccionEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.RestriccionRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioService {
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    RestriccionRepository restriccionRepository;

    @Transactional
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws EntityNotFoundException, IllegalOperationException {
    
    log.info("Inicia proceso de creación del servicio");

        if (servicioEntity.getNombre() == null || servicioEntity.getNombre().isEmpty())
            throw new IllegalOperationException(ErrorMessage.SERVICIO_NAME_REQUIRED);

        if (servicioEntity.getPrecio() == null || servicioEntity.getPrecio() <= 0.0)
            throw new IllegalOperationException(ErrorMessage.SERVICIO_PRICE_REQUIRED);

        if (servicioEntity.getDescripcion() == null || servicioEntity.getDescripcion().isEmpty())
            throw new IllegalOperationException(ErrorMessage.SERVICIO_DESCRIPCION_REQUIRED);

        if (servicioEntity.getImagen() == null || servicioEntity.getImagen().isEmpty())
            throw new IllegalOperationException(ErrorMessage.SERVICIO_IMAGE_REQUIRED);

        log.info("Termina proceso de creacion del servicio");

        return servicioRepository.save(servicioEntity);
    }

    @Transactional
    public List<ServicioEntity> getServicios() throws EntityNotFoundException, IllegalOperationException {

        log.info("Inicia proceso de consultar todos los servicios");

        return servicioRepository.findAll();
    }

    @Transactional
    public ServicioEntity getServicio(Long servicioId) throws EntityNotFoundException, IllegalOperationException {

        log.info("Inicia proceso de consultar el servicio con id = {0}", servicioId);

        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        log.info("Termina proceso de consultar el servicio con id = {0}", servicioId);

        return servicioEntity.get();
    }

    @Transactional
    public ServicioEntity updateServicio(Long servicioId, ServicioEntity servicioEntity) throws EntityNotFoundException, IllegalOperationException {

        log.info("Inicia proceso de actualizar el servicio con id = {0}", servicioId);

        Optional<ServicioEntity> servicioEntityOptional = servicioRepository.findById(servicioId);
        if (servicioEntityOptional.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
        
        servicioEntity.setId(servicioId);
        log.info("Termina proceso de actualizar el servicio con id = {0}", servicioId);
        return servicioRepository.save(servicioEntity);
        
    }

    @Transactional
    public void deleteServicio(Long servicioId) throws EntityNotFoundException, IllegalOperationException {

        log.info("Inicia proceso de borrar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
        servicioRepository.deleteById(servicioId);
        log.info("Termina proceso de el servicio con id = {0}", servicioId);
    }

    @Transactional
    public ServicioEntity addRestriccion(Long servicioId, Long restriccionId) throws EntityNotFoundException, IllegalOperationException {
        
        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("Servicio no encontrado con ID: " + servicioId);

        if (restriccionEntity.isEmpty())
            throw new EntityNotFoundException("Restricción no encontrada con ID: " + restriccionId);

        servicioEntity.get().getRestricciones().add(restriccionEntity.get());
        servicioRepository.save(servicioEntity.get());

        return servicioEntity.get();
    }

    @Transactional
    public RestriccionEntity addServicio(Long restriccionId, Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de añadir el servicio a la restricción con id = {0}", restriccionId);

        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio no encontrado con ID: " + servicioId);
        }

        restriccionEntity.get().setServiciosRestriccion(servicioEntity.get());

        log.info("Finaliza proceso de añadir el servicio a la restricción con id = {0}", restriccionId);

        return restriccionEntity.get();
    }
	
	@Transactional
    public void removeRestriccion(Long servicioId, Long restriccionId) throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicioEntity = getServicio(servicioId);
        RestriccionEntity restriccionEntity = restriccionRepository.findById(restriccionId)
                .orElseThrow(() -> new EntityNotFoundException("Restricción no encontrada con ID: " + restriccionId));
        servicioEntity.getRestricciones().remove(restriccionEntity);
        servicioRepository.save(servicioEntity);
    }
}