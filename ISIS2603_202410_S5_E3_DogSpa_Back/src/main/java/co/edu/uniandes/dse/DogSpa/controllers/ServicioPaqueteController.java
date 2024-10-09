package co.edu.uniandes.dse.DogSpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.DogSpa.dto.PaqueteDTO;
import co.edu.uniandes.dse.DogSpa.dto.PaqueteDetailDTO;

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;

import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;

import co.edu.uniandes.dse.DogSpa.services.ServicioPaqueteService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioPaqueteController {

    @Autowired
    private ServicioPaqueteService servicioPaqueteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{servicioId}/paquetes/{paqueteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public PaqueteDetailDTO getPaquete(@PathVariable("servicioId") Long servicioId, @PathVariable("paqueteId") Long paqueteId)
            throws EntityNotFoundException, IllegalOperationException {
        PaqueteEntity paqueteEntity = servicioPaqueteService.getPaquete(servicioId, paqueteId);
        return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
    }

    @GetMapping(value = "/{servicioId}/paquetes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PaqueteDetailDTO> getPaquetes(@PathVariable("servicioId") Long servicioId) throws EntityNotFoundException {
        List<PaqueteEntity> paqueteEntity = servicioPaqueteService.getPaquetes(servicioId);
        return modelMapper.map(paqueteEntity, new TypeToken<List<PaqueteDetailDTO>>() {
        }.getType());
    }

    @PostMapping(value = "/{servicioId}/paquetes/{paqueteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public PaqueteDetailDTO addPaquete(@PathVariable("servicioId") Long servicioId, @PathVariable("paqueteId") Long paqueteId)
            throws EntityNotFoundException {
        PaqueteEntity paqueteEntity = servicioPaqueteService.addPaquete(servicioId, paqueteId);
        return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
    }


    @PutMapping(value = "/{servicioId}/paquetes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PaqueteDetailDTO> replacePaquetes(@PathVariable("servicioId") Long servicioId, @RequestBody List<PaqueteDTO> paquetes)
            throws EntityNotFoundException {
        List<PaqueteEntity> entities = modelMapper.map(paquetes, new TypeToken<List<PaqueteEntity>>() {
        }.getType());
        List<PaqueteEntity> paqueteList = servicioPaqueteService.replacePaquetes(servicioId, entities);
        return modelMapper.map(paqueteList, new TypeToken<List<PaqueteDetailDTO>>() {
        }.getType());
    }

    @DeleteMapping(value = "/{servicioId}/paquetes/{paqueteId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removePaquete(@PathVariable("servicioId") Long servicioId, @PathVariable("paqueteId") Long paqueteId)
            throws EntityNotFoundException {
        servicioPaqueteService.removePaquete(servicioId, paqueteId);
    }
}