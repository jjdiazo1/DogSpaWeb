import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { PaqueteServicio } from './paqueteServicio';
import { Observable } from 'rxjs';
import { PaqueteServicioDetail } from './paqueteServicioDetail';

@Injectable({
  providedIn: 'root'
})
export class PaqueteServicioService {

  private apiUrl: string = environment.baseUrl + 'paquetes';

  constructor(private http: HttpClient) { }

  getPaquetes(): Observable<PaqueteServicioDetail[]> {
    return this.http.get<PaqueteServicioDetail[]>(this.apiUrl);
  }

  getPaquete(id: string): Observable<PaqueteServicioDetail> {
    return this.http.get<PaqueteServicioDetail>(this.apiUrl + "/" + id);
  }

  getFiltro(id: number): Observable<PaqueteServicioDetail[]> {
    return this.http.get<PaqueteServicioDetail[]>(`${this.apiUrl}/filtro/${id}`);
  }

}
