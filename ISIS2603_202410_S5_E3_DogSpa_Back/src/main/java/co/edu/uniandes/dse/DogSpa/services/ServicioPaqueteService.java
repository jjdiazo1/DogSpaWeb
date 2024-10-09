package co.edu.uniandes.dse.DogSpa.services;

import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioPaqueteService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private PaqueteRepository paqueteRepository;

    @Transactional
    public PaqueteEntity addPaquete(Long servicioId, Long paqueteId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle un paquete al servicio id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
        if (paqueteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

        servicioEntity.get().getPaquetes().add(paqueteEntity.get());
        log.info("Termina proceso de asociarle un paquete al servicio con id = {0}", servicioId);
        return paqueteEntity.get();
    }

    /**
     * Obtiene una colección de instancias de PaqueteEntity asociadas a una instancia
     * de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @return Colección de instancias de PaqueteEntity asociadas a la instancia de
     *         Servicio
     */
    @Transactional
    public List<PaqueteEntity> getPaquetes(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los paquetes del servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
        log.info("Finaliza proceso de consultar todos los paquetes del servicio con id = {0}", servicioId);
        return servicioEntity.get().getPaquetes();
    }

    @Transactional
    public PaqueteEntity getPaquete(Long servicioId, Long paqueteId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar un paquete del servicio con id = {0}", servicioId);
        Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio no fue encontrado");

        if (paqueteEntity.isEmpty())
            throw new EntityNotFoundException("El paquete no fue encontrado");

        log.info("Termina proceso de consultar un paquete del servicio con id = {0}", servicioId);
        if (!servicioEntity.get().getPaquetes().contains(paqueteEntity.get()))
            throw new IllegalOperationException("El paquete no está asociado al servicio");

        return paqueteEntity.get();
    }

    @Transactional
    public List<PaqueteEntity> replacePaquetes(Long servicioId, List<PaqueteEntity> list) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar los paquetes del servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio no fue encontrado");

        for (PaqueteEntity paquete : list) {
            Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paquete.getId());
            if (paqueteEntity.isEmpty())
                throw new EntityNotFoundException("El paquete no fue encontrado");

            if (!servicioEntity.get().getPaquetes().contains(paqueteEntity.get()))
                servicioEntity.get().getPaquetes().add(paqueteEntity.get());
        }
        log.info("Termina proceso de reemplazar los paquetes del servicio con id = {0}", servicioId);
        return servicioEntity.get().getPaquetes();
    }

    @Transactional
    public void removePaquete(Long servicioId, Long paqueteId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un paquete del servicio con id = {0}", servicioId);
        Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if (paqueteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);

        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        servicioEntity.get().getPaquetes().remove(paqueteEntity.get());

        log.info("Termina proceso de borrar un paquete del servicio con id = {0}", servicioId);
    }
}
