package co.edu.uniandes.dse.DogSpa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/*Clase que répresenta un autor en la persistencia
 * 
 * @author ISIS2603
 * 
*/

@Getter
@Setter
@Entity
public class RestriccionEntity extends BaseEntity {
    private Integer edad;
    private String raza;
    private String animal;

    @PodamExclude
    @ManyToOne
    private ServicioEntity serviciosRestriccion;
}



