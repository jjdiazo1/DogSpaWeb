import { Component, Input, OnInit } from '@angular/core';
import { ReservaDetail } from '../reservaDetail';

@Component({
  selector: 'app-reserva-detail',
  templateUrl: './reserva-detail.component.html',
  styleUrls: ['./reserva-detail.component.css'],
})
export class ReservaDetailComponent implements OnInit {
  @Input() reservaDetail!: ReservaDetail;

  constructor() {}

  ngOnInit() {}
}
