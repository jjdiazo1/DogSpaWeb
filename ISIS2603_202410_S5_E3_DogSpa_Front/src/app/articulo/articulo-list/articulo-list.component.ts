import { Component, OnInit } from '@angular/core';

import { Articulo } from '../articulo';
import { ArticuloService } from '../articulo.service';
import { ArticuloDetail } from '../articuloDetail';
import { SedeDetail } from '../../sede/sedeDetail';
import { SedeService } from '../../sede/sede.service';




@Component({
  selector: 'app-articulo-list',
  templateUrl: './articulo-list.component.html',
  styleUrls: ['./articulo-list.component.css']
})
export class ArticuloListComponent implements OnInit {

  selectedArticulo!: ArticuloDetail;
  selected: Boolean = false;
  selectedName: string = ''; 
  filteredArticulos: ArticuloDetail[] = [];
  articulofilter: ArticuloDetail[] = [];
  searchTerm: string = '';
  selectedSede: number = 0;
  articulos: Array<ArticuloDetail> = [];
  sedes: Array<SedeDetail> = [];
 


  onSelected(articulo: ArticuloDetail): void {
    this.selected = true;
    this.selectedArticulo = articulo;
  }

   
  constructor(private articuloService: ArticuloService, private sedeService: SedeService) { }

  getArticulos(): void {
    this.articuloService.getArticulos().subscribe((articulos) => {
      this.articulos = articulos;
      this.filteredArticulos = articulos;
    });
  }

  getSedes(): void {
    this.sedeService.getSedes().subscribe((sedes) => {
      this.sedes = sedes;
      
    });
  }

  getFiltro(): void {
    console.log(`Filtrando artículos para la sede con ID: ${this.selectedSede}`);
    this.articuloService.getFiltro(this.selectedSede).subscribe((articulos) => {
      this.filteredArticulos = articulos;
    });
  }


  searchArticulos(): void {
    this.filteredArticulos = this.articulos.filter(articulo => {
      return articulo.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
             (this.selectedName === '' || articulo.nombre === this.selectedName);
    });
  }

  ngOnInit() {
    this.getArticulos();
    this.getSedes();
  }

}
