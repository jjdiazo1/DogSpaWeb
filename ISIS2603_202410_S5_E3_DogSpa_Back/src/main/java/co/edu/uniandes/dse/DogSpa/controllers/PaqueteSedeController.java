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

import co.edu.uniandes.dse.DogSpa.dto.SedeDTO;
import co.edu.uniandes.dse.DogSpa.dto.SedeDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.PaqueteSedeService;

@RestController
@RequestMapping("/paquetes")
public class PaqueteSedeController {

    @Autowired
    private PaqueteSedeService paqueteSedeService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * Busca y devuelve la sede con el ID recibido en la URL, relativo a un paquete.
	 *
	 * @param paqueteId El ID del paquete del cual se busca la sede
	 * @param sedeId   El ID de la sede que se busca
	 * @return {@link SedeDetailDTO} - El sede encontrada en el paquete.
	 */
	@GetMapping(value = "/{paqueteId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public SedeDetailDTO getSede(@PathVariable("paqueteId") Long paqueteId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity =paqueteSedeService.getSede(paqueteId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}

	/**
	 * Busca y devuelve todas las sedes que existen en un paquete.
	 *
	 * @param paquetesId El ID del paquete del cual se buscan las sedes
	 * @return JSONArray {@link SedeDetailDTO} - Las sedes encontrados en el paquete.
	 *         Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{paqueteId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> getSedes(@PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException {
		List<SedeEntity> sedeEntity = paqueteSedeService.getSedeEntities(paqueteId);
		return modelMapper.map(sedeEntity, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());
	}

	/**
	 * Asocia un sede existente con un paquete existente
	 *
	 * @param paqueteId El ID del paquete al cual se le va a asociar la sede
	 * @param sedeId   El ID dla sede que se asocia
	 * @return JSON {@link SedeDetailDTO} - El sede asociado.
	 */
	@PostMapping(value = "/{paqueteId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public SedeDetailDTO addSede(@PathVariable("paqueteId") Long paqueteId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		SedeEntity sedeEntity = paqueteSedeService.addSede(paqueteId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}

	/**
	 * Actualiza la lista de sedes de un paquete con la lista que se recibe en el
	 * cuerpo
	 *
	 * @param paqueteId El ID del paquete al cual se le va a asociar la sede
	 * @param sedes    JSONArray {@link SedeDTO} - La lista de sedes que se desea
	 *                 guardar.
	 * @return JSONArray {@link SedeDetailDTO} - La lista actualizada.
	 */
	@PutMapping(value = "/{paqueteId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> replaceSedes(@PathVariable("paqueteId") Long paqueteId, @RequestBody List<SedeDTO> sedes)
			throws EntityNotFoundException {
		List<SedeEntity> entities = modelMapper.map(sedes, new TypeToken<List<SedeEntity>>() {
		}.getType());
		List<SedeEntity> sedesList = paqueteSedeService.replaceSedes(paqueteId, entities);
		return modelMapper.map(sedesList, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());

	}

	/**
	 * Elimina la conexión entre la sede y e paquete recibidos en la URL.
	 *
	 * @param paqueteId El ID del paquete al cual se le va a desasociar la sede
	 * @param sedeId   El ID dla sede que se desasocia
	 */
	@DeleteMapping(value = "/{paqueteId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeSede(@PathVariable("paqueteId") Long paqueteId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		paqueteSedeService.removeSede(paqueteId, sedeId);
	}

}


