import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UserCarouselComponent } from './components/user-carousel/user-carousel.component';



@NgModule({
  declarations: [
    UserCardComponent,
    UserCarouselComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    UserCardComponent,
    UserCarouselComponent
  ]
})
export class UserModule { }
