package co.edu.uniandes.dse.DogSpa.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;

@Service
public class ServicioSedeService {
    
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Transactional
    public ServicioEntity addSedeToServicio(Long servicioId, Long sedeId) throws EntityNotFoundException {
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if (!servicioEntity.isPresent())
            throw new EntityNotFoundException("Servicio no encontrado");

        if (!sedeEntity.isPresent())
            throw new EntityNotFoundException("Sede no encontrada");

        servicioEntity.get().getSedes().add(sedeEntity.get());
        return servicioRepository.save(servicioEntity.get());
    }

    @Transactional
    public List<SedeEntity> getSedesFromServicio(Long servicioId) throws EntityNotFoundException {
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (!servicioEntity.isPresent())
            throw new EntityNotFoundException("Servicio no encontrado");

        return servicioEntity.get().getSedes();
    }

    @Transactional
    public void removeSedeFromServicio(Long servicioId, Long sedeId) throws EntityNotFoundException {
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if (!servicioEntity.isPresent() || !sedeEntity.isPresent())
            throw new EntityNotFoundException("Servicio o Sede no encontrada");

        servicioEntity.get().getSedes().remove(sedeEntity.get());
        servicioRepository.save(servicioEntity.get());
    }
}
