import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArticuloListComponent } from './articulo-list/articulo-list.component';
import { ArticuloDetailComponent } from './articulo-detail/articulo-detail.component';


const routes: Routes = [
    {
     path: 'list',
     component: ArticuloListComponent
   },
   {
     path: ':id',
     component: ArticuloDetailComponent
   },
];


@NgModule({
 imports: [RouterModule.forChild(routes)],
 exports: [RouterModule]
})
export class ArticuloRoutingModule { }