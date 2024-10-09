export class Sede {

    id:number;
    nombre: string;
    direccion: string;
    telefono: number;
    instagram: string;
    ciudad: string;

    constructor(
        id:number,
        nombre: string,
        direccion: string,
        telefono: number,
        instagram: string,
        ciudad: string,
    ) {
      this.id = id;
      this.nombre = nombre;
      this.direccion = direccion;
      this.telefono = telefono;
      this.instagram = instagram;
      this.ciudad = ciudad;
    }
   }
