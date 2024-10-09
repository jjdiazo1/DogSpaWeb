package co.edu.uniandes.dse.DogSpa.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.modelmapper.TypeToken;
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
import co.edu.uniandes.dse.DogSpa.services.ArticuloSedeService;


@RestController
@RequestMapping("/articulos")
public class ArticuloSedeController {

    @Autowired
    private ArticuloSedeService articuloSedeService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * Busca y devuelve la sede con el ID recibido en la URL, relativo a un articulo.
	 *
	 * @param articuloId El ID del autor del cual se busca el libro
	 * @param sedeId   El ID del libro que se busca
	 * @return {@link BookDetailDTO} - El libro encontrado en el autor.
	 */
	@GetMapping(value = "/{articuloId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)

	public SedeDetailDTO getSede(@PathVariable("articuloId") Long articuloId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity =articuloSedeService.getSede(articuloId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}

    /**
	 * Busca y devuelve todas las sede que existen en un articulo.
	 */
	@GetMapping(value = "/{articuloId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> getSedes(@PathVariable("articuloId") Long articuloId) throws EntityNotFoundException {
		List<SedeEntity> sedeEntity = articuloSedeService.getSedes(articuloId);
		return modelMapper.map(sedeEntity, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());

	}

	@PutMapping(value = "/{articuloId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> replaceBooks(@PathVariable("articuloId") Long articuloId, @RequestBody List<SedeDTO> sedes)
			throws EntityNotFoundException {
		List<SedeEntity> entities = modelMapper.map(sedes, new TypeToken<List<SedeEntity>>() {
		}.getType());
		List<SedeEntity> sedesList = articuloSedeService.addSedes(articuloId, entities);
		return modelMapper.map(sedesList, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());

	}
	
	/**
	 * Asocia una sede existente con un articulo existente
	 */
	@PostMapping(value = "/{articuloId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public SedeDetailDTO addSede(@PathVariable("articuloId") Long articuloId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		SedeEntity sedeEntity = articuloSedeService.addSede(articuloId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}


	/**
	 * Elimina la conexión entre el libro y e autor recibidos en la URL.
	 */
	@DeleteMapping(value = "/{articuloId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeArticulo(@PathVariable("articuloId") Long authorId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		articuloSedeService.removeSede(authorId, sedeId);;
	}

}
