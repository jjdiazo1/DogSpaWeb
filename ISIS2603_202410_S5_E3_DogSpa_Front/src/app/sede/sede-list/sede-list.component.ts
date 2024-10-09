import { Component, OnInit,ChangeDetectorRef  } from '@angular/core';
import { Sede } from '../sede';
import { SedeService } from '../sede.service';
import { SedeDetail } from '../sedeDetail';
import { ArticuloDetail } from '../../articulo/articuloDetail';
import { ArticuloService } from '../../articulo/articulo.service';


@Component({
  selector: 'app-sede-list',
  templateUrl: './sede-list.component.html',
  styleUrls: ['./sede-list.component.css']
})
export class SedeListComponent implements OnInit {
  selectedSede!: SedeDetail;
  selected: boolean = false;
  sedes: SedeDetail[] = [];
  filteredSedes: SedeDetail[] = [];
  searchTerm: string = '';
  selectedArticulo: number = 0;
  selectedName: string = ''; 
  sedesfilter: Array<SedeDetail> = [];
  articulos: Array<ArticuloDetail> = [];
  isSortedByCity: boolean = false; //
  sedesFiltradas: SedeDetail[] = [];


  constructor(private sedeService: SedeService, private articuloService: ArticuloService,private ref: ChangeDetectorRef) { }

  ngOnInit() {
    this.getSedes();
  }

  /*
  filtrarPorCiudad(): void {
    if (this.searchTerm) {
      this.sedeService.getSedesPorCiudad(this.searchTerm).subscribe((sedes) => {
        this.sedesFiltradas = sedes;
        if (this.sedesFiltradas.length > 0) {
          this.selectedSede = this.sedesFiltradas[0]; // selecciona la primera sede que coincide
          this.selected = true; // muestra el detalle de la sede
        } else {
          this.selectedSede = null; // no hay sedes que coincidan
          this.selected = false; // oculta el detalle de la sede
        }
        this.ref.detectChanges(); 
      });
    } else {
      this.getSedes(); 
    }
  }*/
  
 
  filtrarPorCiudad(): void {
    if (this.searchTerm) {
      this.sedeService.getSedesPorCiudad(this.searchTerm).subscribe((sedes) => {
        this.sedesFiltradas = sedes;
        if (this.sedesFiltradas.length > 0) {
          this.selectedSede = this.sedesFiltradas[0]; 
          this.selected = true; 
        } else {

          this.selected = false; 
        }
        this.ref.detectChanges(); 
      });
    } else {
      this.getSedes(); 
    }
  }
  
  

  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      this.sedes = sedes;
      this.filteredSedes = sedes;
    });
  }


  getArticulos(): void {
    this.articuloService.getArticulos().subscribe((articulos) => {
      this.articulos = articulos;
      
    });}

  
    sortSedesByCity(): void {
      this.isSortedByCity = !this.isSortedByCity;
      this.filteredSedes.sort((a, b) => {
        if (this.isSortedByCity) {
          return a.ciudad.localeCompare(b.ciudad);
        } else {
          return 0; 
        }
      });
    }

  onSelected(sede: SedeDetail): void {
    this.selected = true;
    this.selectedSede = sede;
  }

  searchSedes(): void {
    this.filteredSedes = this.sedes.filter(sede => {
      return sede.ciudad.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
             (this.selectedName === '' || sede.nombre === this.selectedName);
    });
  }


  Atras(): void {
    this.selected = false;
  }
}

