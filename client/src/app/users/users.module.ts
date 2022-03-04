import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { RegisterComponent } from './components/auth/register/register.component';
import { LoginComponent } from './components/auth/login/login.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RegisterComponent, LoginComponent],
  imports: [CommonModule, UsersRoutingModule, FormsModule],
})
export class UsersModule {}
