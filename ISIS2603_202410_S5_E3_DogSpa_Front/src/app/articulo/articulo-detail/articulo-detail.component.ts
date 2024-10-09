import { Component, Input, OnInit } from '@angular/core';
import { Articulo } from '../articulo';
import { ArticuloDetail } from '../articuloDetail';
import { ActivatedRoute } from '@angular/router';
import { ArticuloService } from '../articulo.service';

@Component({
  selector: 'app-articulo-detail',
  templateUrl: './articulo-detail.component.html',
  styleUrls: ['./articulo-detail.component.css']
})
export class ArticuloDetailComponent implements OnInit {

  articuloId!: string;
  @Input() articuloDetail!: ArticuloDetail;
  
    constructor(
    private route: ActivatedRoute,
    private articuloService: ArticuloService
  ) {}

  
  

  getArticulo(){
    this.articuloService.getArticulo(this.articuloId).subscribe(articulo=>{
      this.articuloDetail = articulo;
    })
  }
  
  ngOnInit() {
    if(this.articuloDetail === undefined){
      this.articuloId = this.route.snapshot.paramMap.get('id')!
      if (this.articuloId) {
        this.getArticulo();
      }
    }
  }

}
