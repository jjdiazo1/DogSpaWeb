package co.edu.uniandes.dse.DogSpa.services;

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
public class ArticuloService {

    @Autowired 
    private ArticuloRepository articuloRepository;

    @Autowired
    private SedeRepository sedeRepository;


    
	@Transactional
	 public List<ArticuloEntity> getFiltro( Long sedeId) throws EntityNotFoundException {
		 log.info("Inicia proceso de filtrado");

		 List<ArticuloEntity> articulos = articuloRepository.findAll();
         log.info("articulos", articulos);

		 List<ArticuloEntity> respuesta = new ArrayList<>();

		for (ArticuloEntity articulo : articulos) {

			for (SedeEntity sede : articulo.getSedes()) {
                log.info("sede", sede);
				
                if (sede.getId() == sedeId){
                    respuesta.add(articulo);
                    break;
                }
			}	
		}

        if (0 == sedeId){
            respuesta = articuloRepository.findAll();
            
        }

        log.info("Respuesta del filtro: {}", respuesta);
		 return respuesta;
	 } 


    @Transactional
    public ArticuloEntity createArticulo (ArticuloEntity articuloEntity) throws IllegalOperationException{

        log.info ("Inicia proceso de crecion de un artículo");

        if (articuloEntity.getPrecio() <= 0 ) {
                throw new IllegalOperationException("El número del artículo debe ser un valor positivo");
        }

        if (articuloEntity.getSedes() == null) {
            throw new IllegalOperationException("No se puede crear un articulo sin una sede");
        }

        for (SedeEntity sede : articuloEntity.getSedes()) {
            Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());

            if (sedeEntity.isEmpty()) { 
                throw new IllegalOperationException("La sede no es válida");
            }
        }


        log.info("Termina proceso de creación del libro");
        return articuloRepository.save(articuloEntity);
            
    }

    @Transactional
    public List<ArticuloEntity> getArticulos() {
        log.info("Inicia proceso de consultar todos los articulos");
        return articuloRepository.findAll();
    }

    @Transactional
    public ArticuloEntity getArticulo(Long articuloId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar un articulo con id =", articuloId);
        Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
        if (articuloEntity.isEmpty())
                throw new EntityNotFoundException("No existe ese articulo");
        log.info("Termina proceso de consultar un articulo con id = {0}", articuloId);
        return articuloEntity.get();
    }

    @Transactional
    public ArticuloEntity updateArticulo (Long articuloId, ArticuloEntity articulo)throws EntityNotFoundException, IllegalOperationException {
        
        log.info("Inicia proceso de actualizar un articulo ", articuloId);

        Optional<ArticuloEntity>  articuloEntity = articuloRepository.findById(articuloId);

        if (articuloEntity.isEmpty())
        {
            throw new EntityNotFoundException("No existe ese articulo");
        }

        if (articulo.getPrecio() <= 0 ) {
            throw new IllegalOperationException("El número del artículo debe ser un valor positivo");
        }

        articulo.setId(articuloId);
        return articuloRepository.save(articulo);
    }
    
    @Transactional
    public void deleteArticulo(Long articuloId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el articulo con id = {0}", articuloId);
        Optional<ArticuloEntity> articuloEntity = articuloRepository.findById(articuloId);
        if (articuloEntity.isEmpty())
                throw new EntityNotFoundException("No existe ese articulo");

        List<SedeEntity> sedes = articuloEntity.get().getSedes();

        if (!sedes.isEmpty())
                throw new IllegalOperationException("No se puede borrar el articulo, ya que sigue asociado a las sedes");

        articuloRepository.deleteById(articuloId);
        log.info("Termina proceso de borrar el articulo con id = {0}", articuloId);
}
}
    


