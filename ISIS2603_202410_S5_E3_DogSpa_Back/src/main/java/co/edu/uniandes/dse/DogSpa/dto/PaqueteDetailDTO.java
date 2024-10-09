package co.edu.uniandes.dse.DogSpa.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaqueteDetailDTO extends PaqueteDTO{
        private List<ServicioDTO> servicios = new ArrayList<>();
        private List<ReservaDTO> paquetesDeLaReserva = new ArrayList<>();
        private List<SedeDTO> sedes = new ArrayList<>();
        
}