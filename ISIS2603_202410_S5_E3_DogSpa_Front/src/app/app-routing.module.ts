import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServicioListComponent } from './servicio/servicio-list/servicio-list.component';
import { ArticuloListComponent } from './articulo/articulo-list/articulo-list.component';
import { SedeListComponent } from './sede/sede-list/sede-list.component';
import { PaqueteServicioListComponent } from './paqueteServicio/paqueteServicio-list/paqueteServicio-list.component';
import { HomeComponent } from './home/home.component';
import { ReservaListComponent } from './reserva/reserva-list/reserva-list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: '', component: ArticuloListComponent },
  {
    path: 'articulos',
    loadChildren: () =>
      import('./articulo/articulo.module').then((m) => m.ArticuloModule),
  },
  { path: 'list', component: ServicioListComponent },
  {
    path: 'servicios',
    loadChildren: () =>
      import('./servicio/servicio.module').then((m) => m.ServicioModule),
  },
  { path: '', component: SedeListComponent },
  {
    path: 'sedes',
    loadChildren: () => import('./sede/sede.module').then((m) => m.SedeModule),
  },
  { path: '', component: PaqueteServicioListComponent },
  {
    path: 'paquetes',
    loadChildren: () =>
      import('./paqueteServicio/paqueteServicio.module').then(
        (m) => m.PaqueteServicioModule
      ),
  },
  { path: '', component: ReservaListComponent },
  {
    path: 'reservas',
    loadChildren: () =>
      import('./reserva/reserva.module').then((m) => m.ReservaModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
