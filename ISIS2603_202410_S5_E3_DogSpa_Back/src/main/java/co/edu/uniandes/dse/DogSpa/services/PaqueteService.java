package co.edu.uniandes.dse.DogSpa.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.DogSpa.entities.PaqueteEntity;
import co.edu.uniandes.dse.DogSpa.entities.ReservaEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.entities.ServicioEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.exceptions.ErrorMessage;
import co.edu.uniandes.dse.DogSpa.repositories.PaqueteRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;
import co.edu.uniandes.dse.DogSpa.repositories.ServicioRepository;
//import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Paquete.
 *
 * @author Santiago Gómez
 */

@Slf4j
@Service
public class PaqueteService{

    @Autowired
    PaqueteRepository paqueteRepository;
    @Autowired
    ServicioRepository servicioReposiory;
	@Autowired
    SedeRepository sedeRepository;
        
    /*
	 * Guardar un nuevo libro
	 *
	 * @param bookEntity La entidad de tipo libro del nuevo libro a persistir.
	 * @return La entidad luego de persistirla
	 * @throws IllegalOperationException Si el ISBN es inválido o ya existe en la
	 *                                   persistencia o si la editorial es inválida
	 */


	
	@Transactional
	 public List<PaqueteEntity> getFiltro( Long sedeId) throws EntityNotFoundException {
		 log.info("Inicia proceso de filtrado");

		 List<PaqueteEntity> paquetes = paqueteRepository.findAll();
         log.info("paquetes", paquetes);

		 List<PaqueteEntity> respuesta = new ArrayList<>();

		for (PaqueteEntity paquete : paquetes) {

			for (SedeEntity sede : paquete.getSedes()) {
                log.info("sede", sede);
				
                if (sede.getId() == sedeId){
                    respuesta.add(paquete);
                    break;
                }
			}	
		}
		if (0 == sedeId){
            respuesta = paqueteRepository.findAll();
            
        }

        log.info("Respuesta del filtro: {}", respuesta);
		 return respuesta;
	}

    @Transactional
	public PaqueteEntity createPaquete(PaqueteEntity paqueteEntity) throws IllegalOperationException {
		log.info("Inicia proceso de creación del paquete");
		
		if (paqueteEntity.getNombre() == null)
			throw new IllegalOperationException("Nombre is not valid");

		if (paqueteEntity.getSedes() == null)
			throw new IllegalOperationException("El paquete debe tener al menos una sede");

		if (paqueteEntity.getServicios() == null)
			throw new IllegalOperationException("El paquete debe tener al menos un servicio");
		

		if (paqueteEntity.getPrecio() <= 0)
			throw new IllegalOperationException("Precio is not valid");


		for (SedeEntity sede : paqueteEntity.getSedes()) {

            Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());

            if (sedeEntity.isEmpty()) { 
                throw new IllegalOperationException("La sede no es válida");}
			
            
        } 

		
		for (ServicioEntity servicio : paqueteEntity.getServicios()) {

				Optional<ServicioEntity> servicioEntity = servicioReposiory.findById(servicio.getId());
	
				if (servicioEntity.isEmpty()) { 
					throw new IllegalOperationException("El servicio no es válido");}
				
		} 

		if (paqueteEntity.getPaquetesDeLaReserva() == null)
			throw new IllegalOperationException("Reservas is not valid");

	


		//if (!paqueteRepository.findByIsbn(paqueteEntity.getIsbn()).isEmpty())
		//	throw new IllegalOperationException("ISBN already exists");

		
		log.info("Termina proceso de creación del paquete");
		return paqueteRepository.save(paqueteEntity);
	}
    
	/**
	 * Obtiene la lista de los paquetes.
	 *
	 * @return Colección de objetos de PaqueteEntity.
	 */
	@Transactional
	public List<PaqueteEntity> getEntities() {
		log.info("Inicia proceso de consultar todos los paquetes");
		return paqueteRepository.findAll();
	}

	/**
	 * Obtiene los datos de una instancia de Paquete a partir de su ID.
	 *
	 * @param paqueteId Identificador de la instancia a consultar
	 * @return Instancia de PaqueteEntity con los datos del Paquete consultado.
	 */
	@Transactional
	public PaqueteEntity getPaquete(Long paqueteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar el paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		log.info("Termina proceso de consultar el paquete con id = {0}", paqueteId);
		return paqueteEntity.get();
	}

	/**
	 * Actualiza la información de una instancia de Paquete.
	 *
	 * @param paqueteId     Identificador de la instancia a actualizar
	 * @param paqueteEntity Instancia de PaqueteEntity con los nuevos datos.
	 * @return Instancia de PaqueteEntity con los datos actualizados.
	 */
	@Transactional
	public PaqueteEntity updatePaquete(Long paqueteId, PaqueteEntity paquete) throws EntityNotFoundException, IllegalOperationException
	 {
		log.info("Inicia proceso de actualizar el paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		if (paquete.getNombre() == null)
			throw new IllegalOperationException("Nombre is not valid");
		

		if (paquete.getPrecio() <= 0)
			throw new IllegalOperationException("Precio is not valid");
		
		log.info("Termina proceso de actualizar el paquete con id = {0}", paqueteId);
		paquete.setId(paqueteId);
		return paqueteRepository.save(paquete);
	}

	/**
	 * Elimina una instancia de Paquete de la base de datos.
	 *
	 * @param paqueteId Identificador de la instancia a eliminar.
	 * @throws BusinessLogicException si el autor tiene libros asociados.
	 */
	@Transactional
	public void deletePaquete(Long paqueteId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de borrar el paquete con id = {0}", paqueteId);
		Optional<PaqueteEntity> paqueteEntity = paqueteRepository.findById(paqueteId);
		if (paqueteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PAQUETE_NOT_FOUND);
		
		List<ReservaEntity> reservas = paqueteEntity.get().getPaquetesDeLaReserva();
		if (!reservas.isEmpty())
			throw new IllegalOperationException("No se puede eliminar el paquete porque tiene reservas asociadas");

		List<SedeEntity> sedes = paqueteEntity.get().getSedes();
		if (!sedes.isEmpty())
			throw new IllegalOperationException("No se puede eliminar el paquete porque se encuentra asociado a al menos una sede");
		
		paqueteRepository.deleteById(paqueteId);
		log.info("Termina proceso de borrar el paquete con id = {0}", paqueteId);
	}
}
