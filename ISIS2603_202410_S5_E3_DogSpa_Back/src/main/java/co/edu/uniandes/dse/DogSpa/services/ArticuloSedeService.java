package co.edu.uniandes.dse.DogSpa.services;

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
public class ArticuloSedeService {

	@Autowired
	private ArticuloRepository articuloRepository;

	@Autowired
	private SedeRepository sedeRepository;

	/**
	 * Asocia una Sede existente a un Articulo
	 *
	 * @param articuloId Identificador de la instancia de Articulo
	 * @param sedeId     Identificador de la instancia de Sede
	 * @return Instancia de SedeEntity que fue asociada a Articulo
	 */
	@Transactional
	public SedeEntity addSede(Long articuloId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una sede al articulo con id = {0}", articuloId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);

		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		articuloEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle una sede al articulo con id = {0}", articuloId);
		return sedeEntity.get();
	}

	@Transactional
	public List<SedeEntity> addSedes(Long articuloId, List<SedeEntity> sedes) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las sedes asociados al articulo con id = {0}", articuloId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El artículo no fue encontrado");

		for (SedeEntity sede : sedes) {
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException("La sede no fue encontrada");

			if (!sedeEntity.get().getArticulos().contains(articuloEntity.get()))
				sedeEntity.get().getArticulos().add(articuloEntity.get());
		}
		log.info("Finaliza proceso de reemplazar las sedes asociados al articulo con id = {0}", articuloId);
		articuloEntity.get().setSedes(sedes);
		return articuloEntity.get().getSedes();
	}

	/**
	 * Obtiene una colección de instancias de SedeEntity asociadas a una instancia
	 * de ArticuloEntity
	 *
	 * @param articuloId Identificador de la instancia de Articulo
	 * @return Colección de instancias de SedeEntity asociadas a la instancia de
	 *         Articulo
	 */
	@Transactional
	public List<SedeEntity> getSedes(Long articuloId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las sedes del articulo con id = {0}", articuloId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");
		log.info("Finaliza proceso de consultar todas las sedes del articulo con id = {0}", articuloId);
		return articuloEntity.get().getSedes();
	}

	/**
	 * Obtiene una instancia de AuthorEntity asociada a una instancia de Book
	 *
	 * @param articuloId Identificador de la instancia de Articulo
	 * @param sedeId     Identificador de la instancia de Sede
	 * @return La entidad de la Sede asociada al Articulo
	 */
	@Transactional
	public SedeEntity getSede(Long articuloId, Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una sede del articulo con id = {0}", articuloId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);

		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		log.info("Termina proceso de consultar una sede del articulo con id = {0}", articuloId);
		if (!articuloEntity.get().getSedes().contains(sedeEntity.get()))
			throw new IllegalOperationException("La sede no esta asociada al artículo");

		return sedeEntity.get();
	}

	@Transactional
	/**
	 * Remplaza las instancias de sede asociadas a una instancia de Articulo
	 *
	 * @param articuloId Identificador de la instancia de Articulo
	 * @param list       Colección de instancias de SedeEntity a asociar a instancia
	 *                   de Articulo
	 * @return Nueva colección de SedeEntity asociada a la instancia de Articulo
	 */
	public List<SedeEntity> replaceSedes(Long articuloId, List<SedeEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las sedes del articulo con id = {0}", articuloId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");

		for (SedeEntity sede : list) {
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException("La sede no fue encontrada");

			if (!articuloEntity.get().getSedes().contains(sedeEntity.get()))
				articuloEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Termina proceso de reemplazar las sedes del articulo con id = {0}", articuloId);
		return articuloEntity.get().getSedes();
	}

	@Transactional

	public void removeSede(Long articuloId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una sede del srticulo con id = {0}", articuloId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);

		if (articuloEntity.isEmpty())
			throw new EntityNotFoundException("El articulo no fue encontrado");

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException("La sede no fue encontrada");

		articuloEntity.get().getSedes().remove(sedeEntity.get());

		log.info("Termina proceso de borrar una sede del articulo con id = {0}", articuloId);
	}

}