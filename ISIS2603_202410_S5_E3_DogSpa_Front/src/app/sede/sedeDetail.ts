import { Sede } from "../sede/sede";
import { Articulo } from "../articulo/articulo";
import { Servicio } from "../servicio/servicio";

export class SedeDetail extends Sede{

    articulos: Array<Articulo> = [];
    servicios: Array<Servicio> = [];

    constructor(
        id: number,
        nombre: string,
        direccion: string,
        telefono: number,
        instagram: string,
        ciudad: string,
        articulos: Array<Articulo>,
        servicios: Array<Servicio>,

        
      ) {
        super(id,nombre, direccion, telefono, instagram, ciudad);
        this.articulos = articulos;
        this.servicios = servicios;
      }
}