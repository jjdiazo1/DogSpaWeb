package co.edu.uniandes.dse.DogSpa.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReservaDTO {
  private Long id;
  private String nombreMascota;
  private String duenoMascota;
  private Integer telefono;
  private LocalDateTime fecha;
  private String trabajadorEncargado;
  private String tipoMascota;
  private String razaMascota;
  private Integer edadMascota;
  private SedeDTO sede;
}
