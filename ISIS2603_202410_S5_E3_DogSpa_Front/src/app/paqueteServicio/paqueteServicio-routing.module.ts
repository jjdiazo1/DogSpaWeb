import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PaqueteServicioListComponent } from './paqueteServicio-list/paqueteServicio-list.component';
import { PaqueteServicioDetailComponent } from './paqueteServicio-detail/paqueteServicio-detail.component';


const routes: Routes = [
    {
     path: 'list',
     component: PaqueteServicioListComponent
   },
   {
     path: ':id',
     component: PaqueteServicioDetailComponent
   },
];


@NgModule({
 imports: [RouterModule.forChild(routes)],
 exports: [RouterModule]
})
export class PaqueteServicioRoutingModule { }