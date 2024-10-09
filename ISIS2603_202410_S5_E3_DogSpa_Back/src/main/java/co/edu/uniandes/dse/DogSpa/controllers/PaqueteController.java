package co.edu.uniandes.dse.DogSpa.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.DogSpa.dto.PaqueteDTO;
import co.edu.uniandes.dse.DogSpa.dto.PaqueteDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.PaqueteService;

@RestController
@RequestMapping("/paquetes")
public class PaqueteController {

        @Autowired
        private PaqueteService paqueteService;

        @Autowired
        private ModelMapper modelMapper;

@GetMapping(value = "/filtro/{sedeId}")
@ResponseStatus(code = HttpStatus.OK)
public List<PaqueteDetailDTO> getFiltro(@PathVariable("sedeId") Long sedeId)
	throws EntityNotFoundException, IllegalOperationException {
        List<PaqueteEntity> paquete = paqueteService.getFiltro(sedeId);
	return modelMapper.map(paquete, new TypeToken<List<PaqueteDetailDTO>>() {
}.getType());
}


@GetMapping
@ResponseStatus(code = HttpStatus.OK)
public List<PaqueteDetailDTO> findAll() {
        List<PaqueteEntity> paquetes = paqueteService.getEntities();
        return modelMapper.map(paquetes, new TypeToken<List<PaqueteDetailDTO>>() {
        }.getType());
}

@GetMapping(value = "/{id}")
@ResponseStatus(code = HttpStatus.OK)
public PaqueteDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PaqueteEntity paqueteEntity = paqueteService.getPaquete(id);
        return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
}

@PostMapping
@ResponseStatus(code = HttpStatus.CREATED)
public PaqueteDTO create(@RequestBody PaqueteDTO paqueteDTO) throws IllegalOperationException, EntityNotFoundException {
        PaqueteEntity paqueteEntity = paqueteService.createPaquete(modelMapper.map(paqueteDTO, PaqueteEntity.class));
        return modelMapper.map(paqueteEntity, PaqueteDTO.class);
}

@PutMapping(value = "/{id}")
@ResponseStatus(code = HttpStatus.OK)
public PaqueteDTO update(@PathVariable("id") Long id, @RequestBody PaqueteDTO paqueteDTO)
                        throws EntityNotFoundException, IllegalOperationException {
        PaqueteEntity paqueteEntity = paqueteService.updatePaquete(id, modelMapper.map(paqueteDTO, PaqueteEntity.class));
        return modelMapper.map(paqueteEntity, PaqueteDTO.class);
}

@DeleteMapping(value = "/{id}")
@ResponseStatus(code = HttpStatus.NO_CONTENT)
public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        paqueteService.deletePaquete(id);
}
}