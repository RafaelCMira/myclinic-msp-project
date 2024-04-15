import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {WelcomePageComponent} from "./welcome-page/welcome-page.component";
import {RegisterPatientComponent} from "./register-patient/register-patient.component";
import {LoginComponent} from "./login/login/login.component";

const routes: Routes = [
  { path: '', component: WelcomePageComponent },
  { path: 'register', component: RegisterPatientComponent },
  { path: 'login', component: LoginComponent },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
