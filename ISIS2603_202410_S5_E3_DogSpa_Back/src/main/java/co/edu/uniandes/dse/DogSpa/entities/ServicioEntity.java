package co.edu.uniandes.dse.DogSpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/*Clase que répresenta un autor en la persistencia
 * 
 * @author ISIS2603
 * 
*/

@Data
@Entity
public class ServicioEntity extends BaseEntity {

    private Double precio;
    private String nombre;
    private String descripcion;
    private String imagen;

    @PodamExclude
    @ManyToOne
    private ReservaEntity serviciosDeReserva;

    @PodamExclude
    @ManyToMany
    private List<PaqueteEntity> paquetes = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<SedeEntity> sedes = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "serviciosRestriccion", fetch = FetchType.EAGER)
    private List<RestriccionEntity> restricciones = new ArrayList<>();
}