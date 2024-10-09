import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticuloListComponent } from './articulo-list/articulo-list.component';
import { ArticuloDetailComponent } from './articulo-detail/articulo-detail.component';
import { ArticuloRoutingModule } from './articulo-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms'; 


@NgModule({
  imports: [
    CommonModule, ArticuloRoutingModule,RouterModule, FormsModule
  ],
  exports:[ArticuloListComponent, ArticuloDetailComponent],
  declarations: [ArticuloListComponent, ArticuloDetailComponent]
})
export class ArticuloModule { }
