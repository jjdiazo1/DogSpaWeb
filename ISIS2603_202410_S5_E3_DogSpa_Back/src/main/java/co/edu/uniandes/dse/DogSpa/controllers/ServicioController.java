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

import co.edu.uniandes.dse.DogSpa.dto.ServicioDTO;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicioDTO createServicio(@RequestBody ServicioDTO servicioDTO) throws IllegalOperationException, EntityNotFoundException {
        ServicioEntity servicioEntity = modelMapper.map(servicioDTO, ServicioEntity.class);
        ServicioEntity nuevoServicio = servicioService.createServicio(servicioEntity);
        return modelMapper.map(nuevoServicio, ServicioDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServicioDTO> getServicios() throws EntityNotFoundException, IllegalOperationException {
        List<ServicioEntity> servicios = servicioService.getServicios();
        return modelMapper.map(servicios, new TypeToken<List<ServicioDTO>>() {}.getType());
    }

    @GetMapping("/{servicioId}")
    @ResponseStatus(HttpStatus.OK)
    public ServicioDTO getServicio(@PathVariable Long servicioId) throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicioService.getServicio(servicioId);
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    @PutMapping("/{servicioId}")
    @ResponseStatus(HttpStatus.OK)
    public ServicioDTO updateServicio(@PathVariable Long servicioId, @RequestBody ServicioDTO servicioDTO) throws IllegalOperationException, EntityNotFoundException {
        ServicioEntity servicioEntity = modelMapper.map(servicioDTO, ServicioEntity.class);
        ServicioEntity servicioActualizado = servicioService.updateServicio(servicioId, servicioEntity);
        return modelMapper.map(servicioActualizado, ServicioDTO.class);
    }

    @DeleteMapping("/{servicioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServicio(@PathVariable Long servicioId) throws IllegalOperationException, EntityNotFoundException {
        servicioService.deleteServicio(servicioId);
    }
}
