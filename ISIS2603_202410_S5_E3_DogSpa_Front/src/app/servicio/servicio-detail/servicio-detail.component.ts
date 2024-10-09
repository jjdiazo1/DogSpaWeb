import { Component, OnInit, Input } from '@angular/core';
import { Servicio } from '../servicio';
import { ServicioDetail } from '../servicioDetail';
import { DogFactService } from '../dog-fact.service';


@Component({
  selector: 'app-servicio-detail',
  templateUrl: './servicio-detail.component.html',
  styleUrls: ['./servicio-detail.component.css']
})
export class ServicioDetailComponent implements OnInit {

  @Input() servicioDetail!: ServicioDetail;
  dogFact: string = '';

  constructor(private dogFactService: DogFactService) {}

  ngOnInit(): void {
    this.getDogFact();
  }

  getDogFact(): void {
    this.dogFactService.getDogFact().subscribe((data: any) => {
      if (data && data.message && data.message.length > 0) {
        this.dogFact = data.message;
      }
    }, (error) => {
      console.error('Error fetching dog image:', error);
      this.dogFact = 'Error fetching dog image.';
    });
  }
}