package co.edu.uniandes.dse.DogSpa.entities;


import java.util.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity

public class ArticuloEntity extends BaseEntity {

    private String nombre;
    private String descripcion;
    private float precio;
   
    @PodamExclude
    private String imagen;

    @PodamExclude
    @ManyToMany
    private List<SedeEntity> sedes = new ArrayList<>();
}
