import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Servicio } from './servicio';
import { ServicioDetail } from './servicioDetail';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {
  
  private apiUrl: string = environment.baseUrl + 'servicios';

  constructor(private http: HttpClient) { }

  getServicios(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.apiUrl);
  }

  getServicio(id: string): Observable<ServicioDetail> {
    return this.http.get<ServicioDetail>(this.apiUrl + '/' + id);
  }
}