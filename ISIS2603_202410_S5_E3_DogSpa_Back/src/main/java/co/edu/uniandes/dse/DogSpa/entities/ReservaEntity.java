package co.edu.uniandes.dse.DogSpa.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @author Juan Galdo
 */

@Data
@Entity
public class ReservaEntity extends BaseEntity {
  
  private String nombreMascota;
  private String duenoMascota;
  private Integer telefono;
  private LocalDateTime fecha;
  private String trabajadorEncargado;
  private String tipoMascota;
  private String razaMascota;
  private Integer edadMascota;

  // Relación con Sede
  @PodamExclude
  @ManyToOne
  private SedeEntity sede;

  // Relación con Servicios
  @PodamExclude
  @ManyToMany (mappedBy = "serviciosDeReserva", fetch = FetchType.EAGER)
  private List<ServicioEntity> servicios = new ArrayList<>();

  // Relación con Paquetes
  @PodamExclude
  @ManyToMany(mappedBy = "paquetesDeLaReserva", cascade = CascadeType.PERSIST)
  private List<PaqueteEntity> paquetes = new ArrayList<>();

}