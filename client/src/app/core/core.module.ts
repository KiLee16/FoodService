import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoreRoutingModule } from './core-routing.module';
import { HeaderComponent } from './component/layouts/header/header.component';
import { LandingComponent } from './component/layouts/landing/landing.component';
import { FooterComponent } from './component/layouts/footer/footer.component';

@NgModule({
  //decorators
  declarations: [
    // all components are declared in here
    HeaderComponent,
    LandingComponent,
    FooterComponent,
  ],
  imports: [CommonModule, CoreRoutingModule],
  exports: [
    // list out all components to be exported that is accessible to outside world
    HeaderComponent,
    LandingComponent,
    FooterComponent,
  ],
})
export class CoreModule {}
