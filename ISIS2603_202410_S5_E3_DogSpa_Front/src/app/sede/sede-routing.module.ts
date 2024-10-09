import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SedeListComponent } from './sede-list/sede-list.component';
import { SedeDetailComponent } from './sede-detail/sede-detail.component';


const routes: Routes = [
    {
     path: 'list',
     component: SedeListComponent
   },
   {
     path: ':id',
     component: SedeDetailComponent
   },
];


@NgModule({
 imports: [RouterModule.forChild(routes)],
 exports: [RouterModule]
})
export class SedeRoutingModule { }