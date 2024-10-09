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
import co.edu.uniandes.dse.DogSpa.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.PaqueteServicioService;

@RestController
@RequestMapping("/paquetes")
public class PaqueteServicioController {

    @Autowired
    private PaqueteServicioService paqueteServicioService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * Busca y devuelve el servicio con el ID recibido en la URL, relativo a un paquete.
	 *
	 * @param paqueteId El ID del paquete del cual se busca el servicio
	 * @param servicioId   El ID del servicio que se busca
	 * @return {@link BookDetailDTO} - El servicio encontrado en el paquete.
	 */
	@GetMapping(value = "/{paqueteId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDetailDTO getServicio(@PathVariable("paqueteId") Long authorId, @PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity =paqueteServicioService.getServicio(authorId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
	}

    /**
	 * Busca y devuelve todas los servicios que existen en un paquete.
	 *
	 * @param paquetesId El ID del paquete del cual se buscan los servicios
	 * @return JSONArray {@link ServicioDetailDTO} - Las servicios encontrados en el paquete.
	 *         Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{paqueteId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> getServicios(@PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException {
		List<ServicioEntity> servicioEntity = paqueteServicioService.getServicios(paqueteId);
		return modelMapper.map(servicioEntity, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
	}

	/**
	 * Asocia un servicio existente con un paquete existente
	 *
	 * @param paqueteId El ID del paquete al cual se le va a asociar el servicio
	 * @param servicioId   El ID del servicio que se asocia
	 * @return JSON {@link ServicioDetailDTO} - El servicio asociado.
	 */
	@PostMapping(value = "/{paqueteId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDetailDTO addServicio(@PathVariable("paqueteId") Long paqueteId, @PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException {
		ServicioEntity servicioEntity = paqueteServicioService.addServicio(paqueteId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
	}

	/**
	 * Actualiza la lista de servicios de un paquete con la lista que se recibe en el
	 * cuerpo
	 *
	 * @param paqueteId El ID del paquete al cual se le va a asociar el servicio
	 * @param servicios    JSONArray {@link ServicioDTO} - La lista de servicios que se desea
	 *                 guardar.
	 * @return JSONArray {@link ServicioDetailDTO} - La lista actualizada.
	 */
	@PutMapping(value = "/{paqueteId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> replaceServicios(@PathVariable("paqueteId") Long paqueteId, @RequestBody List<ServicioDTO> servicios)
			throws EntityNotFoundException {
		List<ServicioEntity> entities = modelMapper.map(servicios, new TypeToken<List<ServicioEntity>>() {
		}.getType());
		List<ServicioEntity> serviciosList = paqueteServicioService.replaceServicios(paqueteId, entities);
		return modelMapper.map(serviciosList, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());

	}

	/**
	 * Elimina la conexión entre el servicio y e paquete recibidos en la URL.
	 *
	 * @param paqueteId El ID del paquete al cual se le va a desasociar el servicio
	 * @param servicioId   El ID del servicio que se desasocia
	 */
	@DeleteMapping(value = "/{paqueteId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeServicio(@PathVariable("paqueteId") Long paqueteId, @PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException {
		paqueteServicioService.removeServicio(paqueteId, servicioId);
	}

	

}

