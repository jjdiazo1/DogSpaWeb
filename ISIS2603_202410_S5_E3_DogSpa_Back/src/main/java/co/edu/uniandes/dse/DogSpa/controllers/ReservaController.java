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

import co.edu.uniandes.dse.DogSpa.dto.ReservaDTO;
import co.edu.uniandes.dse.DogSpa.dto.ReservaDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
  
  @Autowired
  private ReservaService reservaService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<ReservaDetailDTO> findAll() {
    List<ReservaEntity> reservas = reservaService.getReservas();
    return modelMapper.map(reservas, new TypeToken<List<ReservaDetailDTO>>() {}.getType());
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public ReservaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
    ReservaEntity reservaEntity = reservaService.getReserva(id);
    return modelMapper.map(reservaEntity, ReservaDetailDTO.class);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ReservaDTO create(@RequestBody ReservaDTO reservaDTO) throws IllegalOperationException, EntityNotFoundException {
    ReservaEntity reservaEntity = reservaService.createReserva(modelMapper.map(reservaDTO, ReservaEntity.class));
    return modelMapper.map(reservaEntity, ReservaDTO.class);
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public ReservaDTO update(@PathVariable("id") Long id, @RequestBody ReservaDTO reservaDTO) throws EntityNotFoundException, IllegalOperationException {
    ReservaEntity reservaEntity = reservaService.updateReserva(id, modelMapper.map(reservaDTO, ReservaEntity.class));
    return modelMapper.map(reservaEntity, ReservaDTO.class);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
    reservaService.deleteReserva(id);
  }
}
