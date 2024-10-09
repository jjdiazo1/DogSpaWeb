import { Component, OnInit } from '@angular/core';
import { Servicio } from '../servicio';
import { ServicioService } from '../servicio.service';

@Component({
  selector: 'app-servicio-list',
  templateUrl: './servicio-list.component.html',
  styleUrls: ['./servicio-list.component.css']
})
export class ServicioListComponent implements OnInit {

  servicios: Array<Servicio> = [];
  selected: Boolean = false;
  selectedServicio!: Servicio;

  constructor(private servicioService: ServicioService) {}

  getServicios(): void {
    this.servicioService.getServicios().subscribe((servicios) => {
      this.servicios = servicios;
    });
  }

  onSelected(servicio: Servicio): void {
    this.selected = true;
    this.selectedServicio = servicio;
  }

  ngOnInit(): void {
    this.getServicios();
  }
}
