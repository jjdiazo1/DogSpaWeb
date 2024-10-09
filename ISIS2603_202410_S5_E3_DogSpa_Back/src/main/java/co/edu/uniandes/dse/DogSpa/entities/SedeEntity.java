package co.edu.uniandes.dse.DogSpa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * *
 * *
 * Clase que representa un autor en la persistencia
 * *
 */

 @Data
 @Entity

 public class SedeEntity extends BaseEntity{
    private String nombre;
    private String direccion;
    private int telefono;
    private String instagram;
    private String ciudad;

    

  @PodamExclude
  @ManyToMany (mappedBy = "sedes",fetch = FetchType.LAZY)
   private List<ArticuloEntity> articulos = new ArrayList<>();

  @PodamExclude
  @ManyToMany (mappedBy = "sedes",fetch = FetchType.LAZY)
   private List<ServicioEntity> servicios = new ArrayList<>();
   
  @PodamExclude
  @OneToMany(mappedBy = "sede", cascade = CascadeType.PERSIST)
   private List<ReservaEntity> reservas = new ArrayList<>();

  @PodamExclude 
  @ManyToMany(mappedBy = "sedes")
   private List<PaqueteEntity> paquetesDeServicios = new ArrayList<>();
 }

   

