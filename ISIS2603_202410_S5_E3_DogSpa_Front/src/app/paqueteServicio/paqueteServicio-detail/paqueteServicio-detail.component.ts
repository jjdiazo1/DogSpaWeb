import { Component, OnInit, Input } from '@angular/core';
import { PaqueteServicio } from '../paqueteServicio';
import { PaqueteServicioDetail } from '../paqueteServicioDetail';
import { ActivatedRoute } from '@angular/router';
import { PaqueteServicioService } from '../paqueteServicio.service';

@Component({
  selector: 'app-paqueteServicio-detail',
  templateUrl: './paqueteServicio-detail.component.html',
  styleUrls: ['./paqueteServicio-detail.component.css']
})
export class PaqueteServicioDetailComponent implements OnInit {
  
  paqueteId!: string;
  @Input() paqueteServicioDetail!: PaqueteServicioDetail

  
  constructor(
    private route: ActivatedRoute,
    private paqueteServicioService: PaqueteServicioService
  ) { }

  getPaquete(){
    this.paqueteServicioService.getPaquete(this.paqueteId).subscribe(paquete=>{
      this.paqueteServicioDetail = paquete;
    })
  }
  
  ngOnInit() {
    if(this.paqueteServicioDetail === undefined){
      this.paqueteId = this.route.snapshot.paramMap.get('id')!
      if (this.paqueteId) {
        this.getPaquete();
      }
    }
  }

}
