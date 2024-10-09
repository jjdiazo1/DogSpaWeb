package co.edu.uniandes.dse.DogSpa.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.DogSpa.dto.SedeDTO;
import co.edu.uniandes.dse.DogSpa.dto.ServicioDTO;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.services.ServicioSedeService;

@RestController
@RequestMapping("/servicios/{servicioId}/sedes")
public class ServicioSedeController {

    @Autowired
    private ServicioSedeService servicioSedeService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SedeDTO> getSedesFromServicio(@PathVariable Long servicioId) throws EntityNotFoundException {
        List<SedeEntity> sedes = servicioSedeService.getSedesFromServicio(servicioId);
        return modelMapper.map(sedes, new TypeToken<List<SedeDTO>>() {}.getType());
    }

    @PostMapping("/{sedeId}")
    @ResponseStatus(HttpStatus.OK)
    public ServicioDTO addSedeToServicio(@PathVariable Long servicioId, @PathVariable Long sedeId) throws EntityNotFoundException {
    ServicioEntity servicio = servicioSedeService.addSedeToServicio(servicioId, sedeId);
    return modelMapper.map(servicio, ServicioDTO.class);
    }


    @DeleteMapping("/{sedeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSedeFromServicio(@PathVariable Long servicioId, @PathVariable Long sedeId) throws EntityNotFoundException {
        servicioSedeService.removeSedeFromServicio(servicioId, sedeId);
    }
}
