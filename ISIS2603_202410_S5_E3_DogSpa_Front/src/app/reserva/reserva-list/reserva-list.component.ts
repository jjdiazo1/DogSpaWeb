import { Component, OnInit } from '@angular/core';
import { ReservaDetail } from '../reservaDetail';
import { ReservaService } from '../reserva.service';

@Component({
  selector: 'app-reserva-list',
  templateUrl: './reserva-list.component.html',
  styleUrls: ['./reserva-list.component.css'],
})
export class ReservaListComponent implements OnInit {
  selectedReserva!: ReservaDetail;
  selected: Boolean = false;
  reservas: Array<ReservaDetail> = [];

  onSelected(reserva: ReservaDetail): void {
    this.selected = true;
    this.selectedReserva = reserva;
  }

  constructor(private reservaService: ReservaService) {}

  getReservas(): void {
    this.reservaService.getReservas().subscribe((reservas) => {
      this.reservas = reservas;
    });
  }

  ngOnInit() {
    this.getReservas();
  }
}
