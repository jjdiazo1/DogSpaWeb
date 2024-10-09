import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReservaListComponent } from './reserva-list/reserva-list.component';
import { ReservaDetailComponent } from './reserva-detail/reserva-detail.component';

const routes: Routes = [
  {
    path: 'list',
    component: ReservaListComponent,
  },
  {
    path: ':id',
    component: ReservaDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReservaRoutingModule {}
