/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { ArticuloListComponent } from './articulo-list.component';
import { HttpClientModule } from '@angular/common/http';
import { Articulo } from '../articulo';
import { ArticuloService } from '../articulo.service';
import { ArticuloDetail } from '../articuloDetail';


describe('ArticuloListComponent', () => {
  let component: ArticuloListComponent;
  let fixture: ComponentFixture<ArticuloListComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ ArticuloListComponent ],
      providers: [ArticuloService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticuloListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    for(let i = 0; i < 10; i++) {
      const articulo = new ArticuloDetail(
        faker.number.int(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.number.int(),
        faker.image.url(),
        []
      );
      
      component.articulos.push(articulo);
    }
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 10 <div.col.mb-2> elements', () => {
    expect(debug.queryAll(By.css('div.col.mb-2'))).toHaveSize(10)
  });

  it('should have 10 <card.p-2> elements', () => {
    const elementos = debug.queryAll(By.css('div.card.p-2'));
    console.log('elementos:', elementos);
    expect(elementos).toHaveSize(10)
  });

  it('should have 10 <img> elements', () => {
    expect(debug.queryAll(By.css('img'))).toHaveSize(10)
  });

  it('should have 10 <div.card-body> elements', () => {
    expect(debug.queryAll(By.css('div.card-body'))).toHaveSize(10)
  });

  it('should have the corresponding src to the articulo image and alt to the articulo name', () => {
    debug.queryAll(By.css('img')).forEach((img, i)=>{
      expect(img.attributes['src']).toEqual(
        component.articulos[i].imagen)
 
      expect(img.attributes['alt']).toEqual(
        component.articulos[i].nombre)
    })
  });

  it('should have h5 tag with the articulo.nombre', () => {
    debug.queryAll(By.css('h5.card-title')).forEach((h5, i)=>{
      expect(h5.nativeElement.textContent).toContain(component.articulos[i].nombre)
    });
  });

  it('should have 9 <div.col.mb-2> elements and the deleted articulo should not exist', () => {
    const articulo = component.articulos.pop()!;
    fixture.detectChanges();
    expect(debug.queryAll(By.css('div.col.mb-2'))).toHaveSize(9)
 
    debug.queryAll(By.css('div.col.mb-2')).forEach((selector, i)=>{
      expect(selector.nativeElement.textContent).not.toContain(articulo.nombre);
    });
  });

});
