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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import co.edu.uniandes.dse.DogSpa.dto.ReservaDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.PaqueteReservaService;


@RestController
@RequestMapping("/paquetes")
public class PaqueteReservaController {

    @Autowired
    private PaqueteReservaService paqueteReservaService;

    @Autowired
    private ModelMapper modelMapper;

    	/**
	 * Busca y devuelve la reserva con el ID recibido en la URL, relativo a un paquete.
	 *
	 * @param paqueteId El ID del paquete del cual se busca la reserva
	 * @param reservaId   El ID de la reserva que se busca
	 * @return {@link ReservaDetailDTO} - El reserva encontrada en el paquete.
	 */
	@GetMapping(value = "/{paqueteId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReservaDetailDTO getReserva(@PathVariable("paqueteId") Long paqueteId, @PathVariable("reservaId") Long reservaId)
			throws EntityNotFoundException, IllegalOperationException {
		ReservaEntity reservaEntity =paqueteReservaService.getReserva(paqueteId, reservaId);
		return modelMapper.map(reservaEntity, ReservaDetailDTO.class);
	}

	/**
	 * Busca y devuelve todas las reservas que existen en un paquete.
	 *
	 * @param paquetesId El ID del paquete del cual se buscan las reservas
	 * @return JSONArray {@link ReservaDetailDTO} - Las reservas encontrados en el paquete.
	 *         Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{paqueteId}/reservas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReservaDetailDTO> getReservas(@PathVariable("paqueteId") Long paqueteId) throws EntityNotFoundException {
		List<ReservaEntity> reservaEntity = paqueteReservaService.getPaquetesDeLaReserva(paqueteId);
		return modelMapper.map(reservaEntity, new TypeToken<List<ReservaDetailDTO>>() {
		}.getType());
	}

	/**
	 * Asocia un reserva existente con un paquete existente
	 *
	 * @param paqueteId El ID del paquete al cual se le va a asociar la reserva
	 * @param reservaId   El ID dla reserva que se asocia
	 * @return JSON {@link ReservaDetailDTO} - El reserva asociado.
	 */
	@PostMapping(value = "/{paqueteId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReservaDetailDTO addReserva(@PathVariable("paqueteId") Long paqueteId, @PathVariable("reservaId") Long reservaId)
			throws EntityNotFoundException {
		ReservaEntity reservaEntity = paqueteReservaService.addReserva(paqueteId, reservaId);
		return modelMapper.map(reservaEntity, ReservaDetailDTO.class);
	}


	/**
	 * Elimina la conexión entre la reserva y e paquete recibidos en la URL.
	 *
	 * @param paqueteId El ID del paquete al cual se le va a desasociar la reserva
	 * @param reservaId   El ID dla reserva que se desasocia
	 */
	@DeleteMapping(value = "/{paqueteId}/reservas/{reservaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeReserva(@PathVariable("paqueteId") Long paqueteId, @PathVariable("reservaId") Long reservaId)
			throws EntityNotFoundException {
		paqueteReservaService.removeReserva(paqueteId, reservaId);
	}
}
