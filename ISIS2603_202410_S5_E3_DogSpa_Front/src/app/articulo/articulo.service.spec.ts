/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ArticuloService } from './articulo.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Articulo', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ArticuloService]
    });
  });

  it('should ...', inject([ArticuloService], (service: ArticuloService) => {
    expect(service).toBeTruthy();
  }));
});
 