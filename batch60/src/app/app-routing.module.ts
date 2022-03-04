import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DatabindingComponent } from './databinding/databinding.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'databinding', component: DatabindingComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
