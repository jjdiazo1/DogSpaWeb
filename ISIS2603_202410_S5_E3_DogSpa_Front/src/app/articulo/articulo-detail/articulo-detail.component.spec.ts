/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ArticuloDetailComponent } from './articulo-detail.component';
import { Sede } from '../../sede/sede';
import { faker } from '@faker-js/faker';
import { ArticuloDetail } from '../articuloDetail';

describe('ArticuloDetailComponent', () => {
  let component: ArticuloDetailComponent;
  let fixture: ComponentFixture<ArticuloDetailComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticuloDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticuloDetailComponent);
    component = fixture.componentInstance;
    
  const sedes: Sede[] = [];
   for (let i = 0; i < 3; i++) {
    const sede = new Sede (
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
    );
    sedes.push(sede);
  }

  component.articuloDetail= new ArticuloDetail(
    faker.number.int(),
    faker.lorem.sentence(),
    faker.lorem.sentence(),
    faker.number.int(),
    faker.image.url(),
    sedes
  );

    
    fixture.detectChanges();
    debug = fixture.debugElement;
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a p.h3.p-3 element with bookDetail.name', () => {
    const element: HTMLElement = debug.query(By.css('p.h3.p-3')).nativeElement;
    expect(element.textContent).toContain(component.articuloDetail.nombre);
  });
});
