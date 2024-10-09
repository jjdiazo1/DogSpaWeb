/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PaqueteServicioService } from './paqueteServicio.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('Service: PaqueteServicio', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PaqueteServicioService]
    });
  });

  it('should ...', inject([PaqueteServicioService], (service: PaqueteServicioService) => {
    expect(service).toBeTruthy();
  }));
});
