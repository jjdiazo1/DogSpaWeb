import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http'; 
import { ServicioListComponent } from './servicio-list/servicio-list.component';
import { ServicioDetailComponent } from './servicio-detail/servicio-detail.component';
import { RouterModule } from '@angular/router';
import { ServicioRoutingModule } from './servicio-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ServicioRoutingModule,
    HttpClientModule
  ],
  exports: [ServicioListComponent],
  declarations: [ServicioListComponent, ServicioDetailComponent]
})
export class ServicioModule { }