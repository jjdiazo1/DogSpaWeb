package co.edu.uniandes.dse.DogSpa.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.DogSpa.dto.ArticuloDTO;
import co.edu.uniandes.dse.DogSpa.dto.ArticuloDetailDTO;
import co.edu.uniandes.dse.DogSpa.dto.SedeDetailDTO;
import co.edu.uniandes.dse.DogSpa.entities.ArticuloEntity;
import co.edu.uniandes.dse.DogSpa.entities.SedeEntity;
import co.edu.uniandes.dse.DogSpa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.DogSpa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.DogSpa.services.ArticuloService;

import java.util.ArrayList;
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


@RestController
@RequestMapping("/articulos")

public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Autowired 
    private ModelMapper modelMapper;



    @GetMapping(value = "/filtro/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ArticuloDetailDTO> getFiltro(@PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		List<ArticuloEntity> articulo = articuloService.getFiltro(sedeId);
		return modelMapper.map(articulo, new TypeToken<List<ArticuloDetailDTO>>() {
        }.getType());
	}


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArticuloDetailDTO> findAll() {
        List<ArticuloEntity> articulo = articuloService.getArticulos();
        return modelMapper.map(articulo, new TypeToken<List<ArticuloDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")

    @ResponseStatus(code = HttpStatus.OK)
    public ArticuloDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ArticuloEntity artciuloEntity = articuloService.getArticulo(id);
        return modelMapper.map(artciuloEntity, ArticuloDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ArticuloDTO create(@RequestBody ArticuloDTO articuloDTO) throws IllegalOperationException, EntityNotFoundException {
        ArticuloEntity articuloEntity = articuloService.createArticulo(modelMapper.map(articuloDTO, ArticuloEntity.class));
        return modelMapper.map(articuloEntity, ArticuloDTO.class);
    }   

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ArticuloDTO update(@PathVariable("id") Long id, @RequestBody ArticuloDTO articuloDTO)
                        throws EntityNotFoundException, IllegalOperationException {
        ArticuloEntity articuloEntity = articuloService.updateArticulo(id, modelMapper.map(articuloDTO, ArticuloEntity.class));
        return modelMapper.map(articuloEntity, ArticuloDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        
        articuloService.deleteArticulo(id);
}

}
