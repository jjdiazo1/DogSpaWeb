import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })
  export class DogFactService {
    private apiUrl = 'https://dog.ceo/api/breeds/image/random';
  
    constructor(private http: HttpClient) {}
  
    getDogFact(): Observable<any> {
      return this.http.get<any>(this.apiUrl);
    }
  }
  
  