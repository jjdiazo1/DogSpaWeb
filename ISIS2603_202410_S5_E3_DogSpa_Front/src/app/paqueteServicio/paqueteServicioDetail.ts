import { Reserva } from "../reserva/reserva";
import { Sede } from "../sede/sede";
import { Servicio } from "../servicio/servicio";
import { PaqueteServicio } from "./paqueteServicio";

export class PaqueteServicioDetail extends PaqueteServicio{
    servicios: Array<Servicio> = [];
    paquetesDeLaReserva: Array<Reserva> = [];
    sedes: Array<Sede> = [];

    constructor(
        id: number,
        nombre:string,
        precio: number,
        servicios: Array<Servicio>,
        paquetesDeLaReserva: Array<Reserva>,
        sedes: Array<Sede>
    ){
        super(id, nombre, precio)
        this.servicios = servicios;
        this.paquetesDeLaReserva = paquetesDeLaReserva;
        this.sedes = sedes;
    }
}
