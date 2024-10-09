package co.edu.uniandes.dse.DogSpa.dto;

import java.util.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticuloDetailDTO extends ArticuloDTO{

    private List<SedeDTO> sedes = new ArrayList<>();

}
