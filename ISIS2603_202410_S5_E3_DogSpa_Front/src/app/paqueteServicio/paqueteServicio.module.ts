import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaqueteServicioListComponent } from './paqueteServicio-list/paqueteServicio-list.component';
import { PaqueteServicioDetailComponent } from './paqueteServicio-detail/paqueteServicio-detail.component';
import { RouterModule } from '@angular/router';
import { PaqueteServicioRoutingModule } from './paqueteServicio-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    PaqueteServicioRoutingModule,
    FormsModule
  ],
  exports: [PaqueteServicioListComponent, PaqueteServicioDetailComponent],
  declarations: [PaqueteServicioListComponent, PaqueteServicioDetailComponent]
})
export class PaqueteServicioModule { }
