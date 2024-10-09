
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { PaqueteServicioService } from './paqueteServicio/paqueteServicio.service';
import { PaqueteServicioListComponent } from './paqueteServicio/paqueteServicio-list/paqueteServicio-list.component';
import { HttpClientModule } from '@angular/common/http';

import { SedeListComponent } from './sede/sede-list/sede-list.component';
import { ArticuloListComponent } from './articulo/articulo-list/articulo-list.component';
import { ServicioListComponent } from './servicio/servicio-list/servicio-list.component';


describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule, HttpClientModule
      ],
      declarations: [
        AppComponent, PaqueteServicioListComponent, SedeListComponent, ArticuloListComponent, ServicioListComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'newAngularApp'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('newAngularApp');
  });

});
