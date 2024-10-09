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

import co.edu.uniandes.dse.DogSpa.services.SedePaqueteService;


@RestController
@RequestMapping("/sedes")
public class SedePaqueteController {
    @Autowired
    private SedePaqueteService sedePaqueteService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * 
	 *
	 * @param sedeId 
	 * @param paqueteId  
	 * @return {@link PaqueteDetailDTO} 
	 */
	@GetMapping(value = "/{sedeId}/paquetes/{paqueteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaqueteDetailDTO getSede(@PathVariable("sedeId") Long sedeId, @PathVariable("paqueteId") Long paqueteId)
			throws EntityNotFoundException, IllegalOperationException {
		PaqueteEntity paqueteEntity =sedePaqueteService.getPaquete(sedeId,paqueteId);
		return modelMapper.map(paqueteEntity, PaqueteDetailDTO.class);
	}

	/**
	 * 
	 *
	 * @param sedesId 
	 * @return JSONArray {@link PaqueteDetailDTO} 
	 */
	@GetMapping(value = "/{sedeId}/paquetes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PaqueteDetailDTO> getPaquete(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<PaqueteEntity> paqueteEntity = sedePaqueteService.getPaqueteEntities(sedeId);
		return modelMapper.map(paqueteEntity, new TypeToken<List<PaqueteDetailDTO>>() {
		}.getType());
	}

	/**
	 * 
	 *@param sedeId  
	 * @param paqueteId 
	 *  
	 * @return JSON {@link PaqueteDetailDTO} - El sede asociado.
	 */
	@PostMapping(value = "/{sedeId}/paquetes/{paqueteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaqueteDetailDTO addPaquete(@PathVariable("sedeId") Long sedeId, @PathVariable("paqueteId") Long paqueteId)
			throws EntityNotFoundException {
		PaqueteEntity paqueteEntity = sedePaqueteService.addPaquete(sedeId, paqueteId);
		return modelMapper.map(paqueteEntity,PaqueteDetailDTO.class);
	}

	/**
	 *
	 * @param sedeId 
	 * @param paquetes    JSONArray {@link PaqueteDTO} 
	 *                 guardar.
	 * @return JSONArray {@link PaqueteDetailDTO} 
	 */
	@PutMapping(value = "/{sedeId}/paquetes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PaqueteDetailDTO> replacePaquetes(@PathVariable("sedeId") Long sedeId, @RequestBody List<PaqueteDTO> paquetes)
			throws EntityNotFoundException {
		List<PaqueteEntity> entities = modelMapper.map(paquetes, new TypeToken<List<PaqueteEntity>>() {
		}.getType());
		List<PaqueteEntity> paquetesList = sedePaqueteService.replacePaquetes(sedeId, entities);
		return modelMapper.map(paquetesList, new TypeToken<List<PaqueteDetailDTO>>() {
		}.getType());

	}

	/**
	
	 *
	 * @param paqueteId 
	 * @param sedeId   
	 */
	@DeleteMapping(value = "/{sedeId}/paquetes/{paqueteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePaquete(@PathVariable("sedeId") Long sedeId, @PathVariable("paqueteId") Long paqueteId)
			throws EntityNotFoundException {
		sedePaqueteService.removePaquete(sedeId, paqueteId);
	}

}
   