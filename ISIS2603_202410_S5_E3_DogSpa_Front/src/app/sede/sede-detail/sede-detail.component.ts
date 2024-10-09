import { Component, Input, OnInit } from '@angular/core';
import { Sede } from '../sede';
import { SedeDetail } from '../sedeDetail';
import { ActivatedRoute } from '@angular/router';
import { SedeService } from '../sede.service';

@Component({
  selector: 'app-sede-detail',
  templateUrl: './sede-detail.component.html',
  styleUrls: ['./sede-detail.component.css']
})
export class SedeDetailComponent implements OnInit {

  sedeId!: string;
  @Input() sedeDetail!: SedeDetail;
  
    constructor(
    private route: ActivatedRoute,
    private sedeService: SedeService
  ) {}

  getSede(){
    
    this.sedeService.getSede(this.sedeId).subscribe(sede=>{
      this.sedeDetail = sede;
    })
  }
  
  ngOnInit() {
    if(this.sedeDetail === undefined){
      
      this.sedeId = this.route.snapshot.paramMap.get('id')!
      console.log(this.sedeId)
      if (this.sedeId) {
        this.getSede();
      }
    }
  }

}