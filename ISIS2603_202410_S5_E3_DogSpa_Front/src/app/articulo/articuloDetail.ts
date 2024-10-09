import { Sede } from "../sede/sede";
import { Articulo } from "./articulo";

export class ArticuloDetail extends Articulo{

    sedes: Array<Sede> = [];


    constructor(
        id: number,
        nombre: string,
        descripcion: string,
        precio: number,
        imagen: string,
        sedes: Array<Sede>,
       
      ) {
        super(id, nombre, descripcion, precio, imagen);
        this.sedes = sedes;
       
      }
}
