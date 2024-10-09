package co.edu.uniandes.dse.DogSpa.entities;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
//import lombok.Getter;
//import lombok.Setter;

import javax.persistence.ManyToMany;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

/* Clase que representa un Paquete de servicios en la persistencia
 * 
 * @autor Santiago Gómez
 */

@Data
@Entity
public class PaqueteEntity extends BaseEntity {
    
    private String nombre;
    private float precio;

    @PodamExclude
    @ManyToMany(mappedBy = "paquetes", cascade = CascadeType.PERSIST)
    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<ReservaEntity> paquetesDeLaReserva = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<SedeEntity> sedes = new ArrayList<>();}
