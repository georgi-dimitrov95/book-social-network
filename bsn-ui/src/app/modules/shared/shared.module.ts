import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { PageNavigationComponent } from './components/page-navigation/page-navigation.component';


@NgModule({
  declarations: [
    PageNavigationComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ],
  exports: [
    PageNavigationComponent
  ]
})
export class SharedModule { }
