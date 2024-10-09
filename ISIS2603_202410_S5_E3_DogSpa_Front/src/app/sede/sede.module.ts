import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SedeComponent } from './sede.component';
import { HttpClientModule } from '@angular/common/http'; 
import { FormsModule } from '@angular/forms';

import { SedeListComponent } from './sede-list/sede-list.component';
import { SedeDetailComponent } from './sede-detail/sede-detail.component';
import { SedeRoutingModule } from './sede-routing.module';
import { RouterModule } from '@angular/router';


@NgModule({
  
  imports: [
    CommonModule,
    SedeRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule
  ],
  exports:[SedeListComponent, SedeDetailComponent],
  declarations: [SedeListComponent, SedeDetailComponent]
})
export class SedeModule { }

