
package co.edu.uniandes.dse.DogSpa.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.repositories.ArticuloRepository;
import co.edu.uniandes.dse.DogSpa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SedeArticuloService {

    @Autowired 
    private ArticuloRepository articuloRepository;

    @Autowired 
    private SedeRepository sedeRepository;

    /**
	 * Asocia un Articulo existente a una Sede
	 *
	 * @param articuloId Identificador de la instancia de Articulo
	 * @param sedeId   Identificador de la instancia de Sede
	 * @return Instancia de Articulo que fue asociada a Sede
	 */

	@Transactional
	public ArticuloEntity addArticulo(Long articuloId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un articulo a la sede con id = {0}", sedeId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo con el id dado no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		sedeEntity.get().getArticulos().add(articuloEntity.get());
		articuloEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle un articulo a la sede con id = {0}", sedeId);
		return articuloEntity.get();
	}

	@Transactional
	public List<ArticuloEntity> addArticulos(Long sedeId, List<ArticuloEntity> articulos) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los libros asociados al author con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		for (ArticuloEntity articulo : articulos) {
			Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articulo.getId());
			if (articuloEntity.isEmpty())
				throw new EntityNotFoundException("El artículo no fue encontrado");

			if (!articuloEntity.get().getSedes().contains(sedeEntity.get()))
				articuloEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los libros asociados al author con id = {0}", sedeId);
		sedeEntity.get().setArticulos(articulos);
		return sedeEntity.get().getArticulos();
	}

    /**
	 * Obtiene una colección de instancias de ArticuloEntity asociadas a una instancia
	 * de sede
	 *
	 * @param sedeId Identificador de la instancia de Author
	 * @return Colección de instancias de ArticuloEntity asociadas a la instancia de
	 *         Author
	 */
	@Transactional
	public List<ArticuloEntity> getArticulos(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los articulos de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		log.info("Termina proceso de consultar todos los articulos de  la sede con id = {0}", sedeId);
		return sedeEntity.get().getArticulos();
	}

	/**
	 * Obtiene una instancia de ArticuloEntity asociada a una instancia de Sede
	 *
	 * @param articuloId Identificador de la instancia de Author
	 * @param sedeId   Identificador de la instancia de Book
	 * @return La entidadd de Libro del autor
	 */
	@Transactional
	public ArticuloEntity getArticulo(Long articuloId, Long sedeId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el articulo con id = {0} de la sede con id = " + articuloId, sedeId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo con el id dado no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");
		
		log.info("Termina proceso de consultar el articulo con id = {0} de la sede con id = " + articuloId, sedeId);
		
		if (!sedeEntity.get().getArticulos().contains(articuloEntity.get()))
			throw new IllegalOperationException("El articulo no esta asociado a ninguna sede");
		
		return articuloEntity.get();
	}

	/**
	 * Remplaza las instancias de Articulo asociadas a una instancia de Sede
	 *
	 * @param sedeId Identificador de la instancia de Sede
	 * @param articuloss    Colección de instancias de ArticuloEntity a asociar a instancia
	 *                 de Sede
	 * @return Nueva colección de ArticuloEntity asociada a la instancia de Sede
	 */
	@Transactional
	public List<ArticuloEntity> replaceArticulos(Long sedeId, List<ArticuloEntity> articulos) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los articulos asociados a la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity= sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		for (ArticuloEntity articulo : articulos) {
			Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articulo.getId());
			if (articuloEntity.isEmpty())
				throw new EntityNotFoundException("El articulo no fue encontrado");

			if (!articuloEntity.get().getSedes().contains(sedeEntity.get()))
				articuloEntity.get().getSedes().contains(sedeEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los articulos asociados a la sede con id = {0}", sedeId);
		sedeEntity.get().setArticulos(articulos);;
		return sedeEntity.get().getArticulos();
	}

	/**
	 * Desasocia un Book existente de un Author existente
	 *
	 * @param sedeId Identificador de la instancia de Author
	 * @param long1   Identificador de la instancia de Book
	 */
	@Transactional
	public void removeArticulo(Long sedeId, Long long1) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un articulo de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(long1);
		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");

		articuloEntity.get().getSedes().remove(sedeEntity.get());
		sedeEntity.get().getArticulos().remove(articuloEntity.get());
		log.info("Finaliza proceso de borrar un articulo de la sede con id = {0}", sedeId);
	}
}