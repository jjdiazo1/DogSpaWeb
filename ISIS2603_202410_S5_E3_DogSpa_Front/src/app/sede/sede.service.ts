import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { SedeDetail } from './sedeDetail';

@Injectable({
  providedIn: 'root'
})
export class SedeService {
  private apiUrl: string = environment.baseUrl + 'sedes';

  constructor(private http: HttpClient) { }

  getSedes(): Observable<SedeDetail[]> {
    return this.http.get<SedeDetail[]>(this.apiUrl);
  }

  getSede(id: string): Observable<SedeDetail> {
    return this.http.get<SedeDetail>(this.apiUrl + "/" + id);
  }

  getSedesByCiudad(ciudad: string): Observable<SedeDetail[]> {
    return this.http.get<SedeDetail[]>(`${this.apiUrl}/ciudad/${ciudad}`);
  }

  getSedesPorCiudad(ciudad: string): Observable<SedeDetail[]> {
    return this.http.get<SedeDetail[]>(`${this.apiUrl}/ciudad/${ciudad}`);
}


}
