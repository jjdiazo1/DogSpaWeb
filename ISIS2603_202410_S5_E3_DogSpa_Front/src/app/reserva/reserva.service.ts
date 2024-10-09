import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { ReservaDetail } from './reservaDetail';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {
  private apiUrl: string = environment.baseUrl + 'reservas';

  constructor(private http: HttpClient) {}

  getReservas(): Observable<ReservaDetail[]> {
    return this.http.get<ReservaDetail[]>(this.apiUrl);
  }
}
