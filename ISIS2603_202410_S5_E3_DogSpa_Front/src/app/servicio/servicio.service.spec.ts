/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ServicioService } from './servicio.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Servicio', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ServicioService]
    });
  });

  it('should ...', inject([ServicioService], (service: ServicioService) => {
    expect(service).toBeTruthy();
  }));
});
