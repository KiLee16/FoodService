import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DatabindingComponent } from './databinding/databinding.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { RegisterComponent } from './register/register.component';

@NgModule({
  declarations: [AppComponent, DatabindingComponent, LoginComponent, IndexComponent, RegisterComponent],
  imports: [BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
