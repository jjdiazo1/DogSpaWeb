import { NgModule } from '@angular/core';

import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';



//Modules
import { PaqueteServicioModule } from './paqueteServicio/paqueteServicio.module';
import { ReservaModule } from './reserva/reserva.module';
import { SedeModule } from './sede/sede.module';
import { ArticuloModule } from './articulo/articulo.module';
import { ServicioModule } from './servicio/servicio.module';
import { HttpClientModule } from '@angular/common/http';
import { ArticuloRoutingModule } from './articulo/articulo-routing.module';
import { DogFactService } from './servicio/dog-fact.service';
import { FormsModule } from '@angular/forms'; 


//Routing
import { ServicioRoutingModule } from './servicio/servicio-routing.module';
import { HomeComponent } from './home/home.component';
import { SedeComponent } from './sede/sede.component';

import { SedeRoutingModule } from './sede/sede-routing.module';
import { ArticuloListComponent } from './articulo/articulo-list/articulo-list.component';
import { PaqueteServicioRoutingModule } from './paqueteServicio/paqueteServicio-routing.module';

@NgModule({
  declarations: [	AppComponent,
      HomeComponent,
      SedeComponent,
      
      
   ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    SedeModule,
    ArticuloModule,
    ServicioModule,
    ServicioRoutingModule,
    PaqueteServicioModule,
    HttpClientModule,
    ReservaModule,
    ArticuloRoutingModule,
    SedeRoutingModule,
    PaqueteServicioRoutingModule,
    FormsModule
  ],
  providers: [
    provideClientHydration(),
    DogFactService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
