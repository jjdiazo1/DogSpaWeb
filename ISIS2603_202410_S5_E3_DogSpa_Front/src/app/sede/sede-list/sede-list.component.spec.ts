/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { SedeListComponent } from './sede-list.component';
import { HttpClientModule } from '@angular/common/http';
import { Sede } from '../sede';
import { SedeService } from '../sede.service';
import { SedeDetail } from '../sedeDetail';

describe('SedeListComponent', () => {
  let component: SedeListComponent;
  let fixture: ComponentFixture<SedeListComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ SedeListComponent ],
      providers: [SedeService]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SedeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
