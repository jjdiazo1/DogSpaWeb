package co.edu.uniandes.dse.DogSpa.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ReservaDetailDTO extends ReservaDTO {
  private List<ServicioDTO> servicios = new ArrayList<>();
  private List<PaqueteDTO> paquetes = new ArrayList<>();
}
