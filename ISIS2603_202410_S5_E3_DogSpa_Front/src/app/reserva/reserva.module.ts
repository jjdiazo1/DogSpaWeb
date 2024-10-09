import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservaListComponent } from './reserva-list/reserva-list.component';
import { ReservaDetailComponent } from './reserva-detail/reserva-detail.component';
import { ReservaRoutingModule } from './reserva-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [CommonModule, ReservaRoutingModule, RouterModule, FormsModule],
  exports: [ReservaListComponent, ReservaDetailComponent],
  declarations: [ReservaListComponent, ReservaDetailComponent],
})
export class ReservaModule {}
