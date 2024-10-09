export class Servicio {
    id: number;
    precio: number;
    nombre: string;
    descripcion: string;
    imagen: string;

    constructor(
        id: number,
        precio: number,
        nombre: string,
        descripcion: string,
        imagen: string
    ) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
}