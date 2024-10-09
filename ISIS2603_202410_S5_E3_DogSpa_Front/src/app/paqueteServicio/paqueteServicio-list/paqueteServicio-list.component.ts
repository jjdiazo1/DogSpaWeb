import { Component, OnInit } from '@angular/core';
import { PaqueteServicio } from '../paqueteServicio';
import { PaqueteServicioService } from '../paqueteServicio.service';
import { PaqueteServicioDetail } from '../paqueteServicioDetail';
import { SedeDetail } from '../../sede/sedeDetail';
import { SedeService } from '../../sede/sede.service';

@Component({
  selector: 'app-paqueteServicio-list',
  templateUrl: './paqueteServicio-list.component.html',
  styleUrls: ['./paqueteServicio-list.component.css']
})
export class PaqueteServicioListComponent implements OnInit {

  selectedPaquete!: PaqueteServicioDetail;
  selected: Boolean = false;
  selectedN: string = ''; 
  filteredPaquetes: PaqueteServicioDetail[] = [];
  paquetefilter: PaqueteServicioDetail[] = [];
  searchTerm: string = '';
  selectedSede: number = 0;
  paquetes: Array<PaqueteServicioDetail> = [];
  sedes: Array<SedeDetail> = [];

  constructor(private paqueteServicioService: PaqueteServicioService, private sedeService: SedeService) { }

  getPaquetes(): void {
    this.paqueteServicioService.getPaquetes().subscribe((paquetes) => {
      this.paquetes = paquetes;
      this.filteredPaquetes = paquetes;
    });
  }

  getSedes(): void {
    this.sedeService.getSedes().subscribe((sedes) => {
      this.sedes = sedes;
      
    });
  }

  getFiltro(): void {
    console.log(`Filtrando paquetes para la sede con ID: ${this.selectedSede}`);
    this.paqueteServicioService.getFiltro(this.selectedSede).subscribe((paquetes) => {
      this.filteredPaquetes = paquetes;
    });
  }

  searchPaquetes(): void {
    this.filteredPaquetes = this.paquetes.filter(paquete => {
      return paquete.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
             (this.selectedN === '' || paquete.nombre === this.selectedN);
    });
  }

  ngOnInit() {
    this.getPaquetes();
    this.getSedes();
  }

  onSelected(paquete: PaqueteServicioDetail): void {
    this.selected = true;
    this.selectedPaquete = paquete;
  }

  Atras(): void {
    this.selected = false;
  }

}
