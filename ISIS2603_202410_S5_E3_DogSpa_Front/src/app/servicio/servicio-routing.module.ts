import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServicioListComponent } from './servicio-list/servicio-list.component';
import { ServicioDetailComponent } from './servicio-detail/servicio-detail.component';

const routes: Routes = [
    {
     path: 'list',
     component: ServicioListComponent
   },
   {
     path: ':id',
     component: ServicioDetailComponent
   },
];


@NgModule({
 imports: [RouterModule.forChild(routes)],
 exports: [RouterModule]
})
export class ServicioRoutingModule { }