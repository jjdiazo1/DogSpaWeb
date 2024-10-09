package co.edu.uniandes.dse.DogSpa.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.DogSpa.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.ReservaServicioService;

@RestController
@RequestMapping("/reservas")
public class ReservaServicioController {
  
  @Autowired
  private ReservaServicioService reservaServicioService;

  @Autowired
    private ModelMapper modelMapper;

  // Devuelve el servicio asociado a una reserva
  @GetMapping(value = "/{reservaId}/servicios/{servicioId}")
  @ResponseStatus(code = HttpStatus.OK)
  public ServicioDetailDTO getServicio(@PathVariable("reservaId") Long reservaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException, IllegalOperationException {
    ServicioEntity servicioEntity = reservaServicioService.getServicio(reservaId, servicioId);
    return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
  }

  // Asociar un servicio existente a una reserva existente
  @PostMapping(value = "/{reservaId}/servicios/{servicioId}")
  @ResponseStatus(code = HttpStatus.OK)
  public ServicioDetailDTO addServicio(@PathVariable("reservaId") Long reservaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException {
    ServicioEntity servicioEntity = reservaServicioService.addServicio(servicioId, reservaId);
    return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
  }

  // Borrar un servicio de una reserva
  @DeleteMapping(value = "/{reservaId}/servicios/{servicioId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void removeServicio(@PathVariable("reservaId") Long reservaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException {
    reservaServicioService.removeServicio(servicioId, reservaId);
  }
}
