/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SedeDetailComponent } from './sede-detail.component';
import { Articulo } from '../../articulo/articulo';
import { Servicio } from '../../servicio/servicio';
import { faker } from '@faker-js/faker';
import { SedeDetail } from '../sedeDetail';

describe('SedeDetailComponent', () => {
  let component: SedeDetailComponent;
  let fixture: ComponentFixture<SedeDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SedeDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SedeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});