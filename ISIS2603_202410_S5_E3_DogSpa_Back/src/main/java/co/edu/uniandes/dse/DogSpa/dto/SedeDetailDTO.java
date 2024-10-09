package co.edu.uniandes.dse.DogSpa.dto;

import java.util.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class SedeDetailDTO extends SedeDTO {
    private List<ServicioDTO> servicios = new ArrayList<>();
    private List<ReservaDTO> paquetesDeLaReserva = new ArrayList<>();
    private List<ArticuloDTO> articulos = new ArrayList<>();
    private List<PaqueteDTO> paquetes = new ArrayList<>();
}
