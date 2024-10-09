import * as moment from 'moment';
type LocalDateTime = moment.Moment;

export class Reserva {
  id: number;
  nombreMascota: string;
  duenoMascota: string;
  telefono: number;
  fecha: LocalDateTime;
  trabajadorEncargado: string;
  tipoMascota: string;
  razaMascota: string;
  edadMascota: number;

  constructor(
    id: number,
    nombreMascota: string,
    duenoMascota: string,
    telefono: number,
    fecha: LocalDateTime,
    trabajadorEncargado: string,
    tipoMascota: string,
    razaMascota: string,
    edadMascota: number
  ) {
    this.id = id;
    this.nombreMascota = nombreMascota;
    this.duenoMascota = duenoMascota;
    this.telefono = telefono;
    this.fecha = fecha;
    this.trabajadorEncargado = trabajadorEncargado;
    this.tipoMascota = tipoMascota;
    this.razaMascota = razaMascota;
    this.edadMascota = edadMascota;
  }
}
