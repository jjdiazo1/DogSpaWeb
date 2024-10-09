/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ReservaService } from './reserva.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Reserva', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReservaService],
    });
  });

  it('should ...', inject([ReservaService], (service: ReservaService) => {
    expect(service).toBeTruthy();
  }));
});
