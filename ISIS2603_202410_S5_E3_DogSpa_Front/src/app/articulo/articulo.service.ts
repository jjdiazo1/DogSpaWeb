import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Articulo } from './articulo';
import { Observable } from 'rxjs';
import { ArticuloDetail } from './articuloDetail';


@Injectable({
  providedIn: 'root'
})
export class ArticuloService {

private apiUrl: string = environment.baseUrl + 'articulos';

constructor(private http: HttpClient) { }

getArticulos(): Observable<ArticuloDetail[]> {
  return this.http.get<ArticuloDetail[]>(this.apiUrl);
}

getArticulo(id: string): Observable<ArticuloDetail> {
  return this.http.get<ArticuloDetail>(this.apiUrl + "/" + id);
}

getFiltro(id: number): Observable<ArticuloDetail[]> {
  return this.http.get<ArticuloDetail[]>(`${this.apiUrl}/filtro/${id}`);
}

}
