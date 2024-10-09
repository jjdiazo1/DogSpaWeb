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

import co.edu.uniandes.dse.DogSpa.services.SedeReservaService;


@RestController
@RequestMapping("/sedes")
public class SedeReservaController {
    @Autowired
    private SedeReservaService sedeReservaService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * 
	 *
	 * @param sedeId 
	 * @param reservaId  
	 * @return {@link ReservaDetailDTO} 
	 */
	@GetMapping(value = "/{sedeId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReservaDetailDTO getSede(@PathVariable("sedeId") Long sedeId, @PathVariable("reservaId") Long reservaId)
			throws EntityNotFoundException, IllegalOperationException {
		ReservaEntity reservaEntity =sedeReservaService.getReserva(sedeId,reservaId);
		return modelMapper.map(reservaEntity, ReservaDetailDTO.class);
	}

	/**
	 * 
	 *
	 * @param sedesId 
	 * @return JSONArray {@link ReservaDetailDTO} 
	 */
	@GetMapping(value = "/{sedeId}/reservas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReservaDetailDTO> getReserva(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ReservaEntity> reservaEntity = sedeReservaService.getReservaEntities(sedeId);
		return modelMapper.map(reservaEntity, new TypeToken<List<ReservaDetailDTO>>() {
		}.getType());
	}

	/**
	 * 
	 *@param sedeId  
	 * @param reservaId 
	 *  
	 * @return JSON {@link ReservaDetailDTO} - El sede asociado.
	 */
	@PostMapping(value = "/{sedeId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReservaDetailDTO addReserva(@PathVariable("reservaId") Long reservaId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		ReservaEntity reservaEntity = sedeReservaService.addReserva(reservaId,sedeId);
		return modelMapper.map(reservaEntity,ReservaDetailDTO.class);
	}

	/**
	 *
	 * @param sedeId 
	 * @param reservas    JSONArray {@link ReservaDTO} 
	 *                 guardar.
	 * @return JSONArray {@link ReservaDetailDTO} 
	 */
	@PutMapping(value = "/{sedeId}/reservas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReservaDetailDTO> replaceReservas(@PathVariable("sedeId") Long sedeId, @RequestBody List<ReservaDTO> reservas)
			throws EntityNotFoundException {
		List<ReservaEntity> entities = modelMapper.map(reservas, new TypeToken<List<ReservaEntity>>() {
		}.getType());
		List<ReservaEntity> reservasList = sedeReservaService.replaceReservas(sedeId, entities);
		return modelMapper.map(reservasList, new TypeToken<List<ReservaDetailDTO>>() {
		}.getType());

	}

	/**
	
	 *
	 * @param reservaId 
	 * @param sedeId   
	 */
	@DeleteMapping(value = "/{sedeId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeReserva(@PathVariable("sedeId") Long sedeId, @PathVariable("reservaId") Long reservaId)
			throws EntityNotFoundException {
		sedeReservaService.removeReserva(sedeId,reservaId);
	}

}
    
