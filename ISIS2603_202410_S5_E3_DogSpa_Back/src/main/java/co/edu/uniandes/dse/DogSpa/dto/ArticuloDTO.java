package co.edu.uniandes.dse.DogSpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ArticuloDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private float precio;
    private String imagen;
}
