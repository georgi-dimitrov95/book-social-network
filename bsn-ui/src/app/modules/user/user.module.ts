import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UserCarouselComponent } from './components/user-carousel/user-carousel.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
    declarations: [
        UserCardComponent,
        UserCarouselComponent
    ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  exports: [
    UserCardComponent,
    UserCarouselComponent
  ]
})
export class UserModule { }
