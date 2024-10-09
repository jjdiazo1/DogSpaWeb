package co.edu.uniandes.dse.DogSpa.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.DogSpa.dto.PaqueteDTO;
import co.edu.uniandes.dse.DogSpa.dto.PaqueteDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.ReservaPaqueteService;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/reservas")
public class ReservaPaqueteController {
  
  @Autowired
  private ReservaPaqueteService reservaPaqueteService;

  @Autowired
    private ModelMapper modelMapper;

  // Devuelve el paquete asociado a una reserva
  @GetMapping(value = "/{reservaId}/paquetes/{paqueteId}")
  @ResponseStatus(code = HttpStatus.OK)
  public PaqueteDetailDTO getPaquete(@PathVariable("reservaId") Long reservaId, @PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException, IllegalOperationException {
    PaqueteEntity paqueteEntity = reservaPaqueteService.getPaquete(paqueteId, reservaId);
    return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
  }

  // Devuelve los paquetes asociados a una reserva
  @GetMapping(value = "/{reservaId}/paquetes")
  @ResponseStatus(code = HttpStatus.OK)
  public List<PaqueteDetailDTO> getPaquetes(@PathVariable("reservaId") Long reservaId) throws EntityNotFoundException {
    List<PaqueteEntity> paqueteEntity = reservaPaqueteService.getPaquetes(reservaId);
    return modelMapper.map(paqueteEntity, new TypeToken<List<PaqueteDetailDTO>>() {
    }.getType());
  }

  // Asociar un paquete existente a una reserva existente
  @PostMapping(value = "/{reservaId}/paquetes/{paqueteId}")
  @ResponseStatus(code = HttpStatus.OK)
  public PaqueteDetailDTO addPaquete(@PathVariable("reservaId") Long reservaId, @PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException {
    PaqueteEntity paqueteEntity = reservaPaqueteService.addPaquete(paqueteId, reservaId);
    return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
  }

  // Actualizar la lista de paquetes de una reserva
  @PutMapping(value = "/{reservaId}/paquetes")
  @ResponseStatus(code = HttpStatus.OK)
  public List<PaqueteDetailDTO> replacePaquetes(@PathVariable("reservaId") Long reservaId, @RequestBody List<PaqueteDTO> paquetes) throws EntityNotFoundException {
      List<PaqueteEntity> entities = modelMapper.map(paquetes, new TypeToken<List<PaqueteEntity>>() {}.getType());
      List<PaqueteEntity> paquetesList = reservaPaqueteService.addPaquetes(reservaId, entities);
      return modelMapper.map(paquetesList, new TypeToken<List<PaqueteDetailDTO>>(){}.getType());
  }

  @DeleteMapping(value = "/{reservaId}/paquetes/{paqueteId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void removePaquete(@PathVariable("reservaId") Long reservaId, @PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException {
    reservaPaqueteService.removePaquete(paqueteId, reservaId);
  }
}
