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

import co.edu.uniandes.dse.DogSpa.dto.ArticuloDTO;
import co.edu.uniandes.dse.DogSpa.dto.ArticuloDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.SedeArticuloService;

@RestController
@RequestMapping("/sedes")
public class SedeArticuloController {

     @Autowired
    private SedeArticuloService sedeArticuloService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{sedeId}/articulos/{articuloId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ArticuloDetailDTO getArticulo(@PathVariable("sedeId") Long sedeId, @PathVariable("articuloId") Long articuloId)
			throws EntityNotFoundException, IllegalOperationException {
		ArticuloEntity articuloEntity = sedeArticuloService.getArticulo(articuloId, sedeId);
		return modelMapper.map(articuloEntity, ArticuloDetailDTO.class);
	}

	@GetMapping(value = "/{sedeId}/articulos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ArticuloDetailDTO> getArticulos(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ArticuloEntity> bookEntity = sedeArticuloService.getArticulos(sedeId);
		return modelMapper.map(bookEntity, new TypeToken<List<ArticuloDetailDTO>>() {
		}.getType());
	}

	@PostMapping(value = "/{sedeId}/articulos/{articuloId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ArticuloDetailDTO addArticulo(@PathVariable("articuloId") Long articuloId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		ArticuloEntity articuloEntity = sedeArticuloService.addArticulo(articuloId, sedeId);
		return modelMapper.map(articuloEntity, ArticuloDetailDTO.class);
	}

	/**
	 * Actualiza la lista de libros de un autor con la lista que se recibe en el
	 * cuerpo
	 *
	 * @param authorId El ID del autor al cual se le va a asociar el libro
	 * @param books    JSONArray {@link BookDTO} - La lista de libros que se desea
	 *                 guardar.
	 * @return JSONArray {@link BookDetailDTO} - La lista actualizada.
	 */
	@PutMapping(value = "/{sedeId}/articulos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ArticuloDetailDTO> replaceArticulos(@PathVariable("sedeId") Long sedeId, @RequestBody List<ArticuloDTO> articulos)
			throws EntityNotFoundException {
		List<ArticuloEntity> entities = modelMapper.map(articulos, new TypeToken<List<ArticuloEntity>>() {
		}.getType());
		List<ArticuloEntity> articulosList = sedeArticuloService.addArticulos(sedeId, entities);
		return modelMapper.map(articulosList, new TypeToken<List<ArticuloDetailDTO>>() {
		}.getType());

	}

	/**
	 * Elimina la conexión entre el libro y e autor recibidos en la URL.
	 *
	 * @param authorId El ID del autor al cual se le va a desasociar el libro
	 * @param bookId   El ID del libro que se desasocia
	 */
	@DeleteMapping(value = "/{sedeId}/articulos/{articuloId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeArticulo(@PathVariable("sedeId") Long sedeId, @PathVariable("articuloId") Long articuloId)
			throws EntityNotFoundException {
		sedeArticuloService.removeArticulo(sedeId, articuloId);;
	}

}
