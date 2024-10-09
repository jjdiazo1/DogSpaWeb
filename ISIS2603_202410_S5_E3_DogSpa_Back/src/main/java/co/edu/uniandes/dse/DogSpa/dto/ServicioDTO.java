package co.edu.uniandes.dse.DogSpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioDTO {
    private Long id;
    private Double precio;
    private String nombre;
    private String imagen;
    private String descripcion;

}