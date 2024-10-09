package co.edu.uniandes.dse.DogSpa.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioDetailDTO extends ServicioDTO {
    private List<PaqueteDTO> paquetes;
    private List<SedeDTO> sedes;
    private List<RestriccionDTO> restricciones;
}