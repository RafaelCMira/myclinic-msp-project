import {Injectable, NgModule} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterModule,
  RouterStateSnapshot,
  Routes,
  UrlTree
} from '@angular/router';
import { WelcomePageComponent } from "./welcome-page/welcome-page.component";
import { RegisterComponent } from "./register/register.component";
import { ScheduleAppointmentComponent } from "./schedule-appointment/schedule-appointment.component";
import { LoginComponent } from "./login/login/login.component";
import { WelcomePanelComponent } from "./welcome-panel/welcome-panel.component";
import { RoleService } from "./services/role/role.service";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private roleService: RoleService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.roleService.isLoggedIn()) {
      return true; // Allow access for logged-in users
    } else {
      this.router.navigate(['/welcome']); // Redirect non-logged-in users to the welcome page
      return false;
    }
  }
}

const routes: Routes = [
  // Default route for logged-in users
  {
    path: '',
    canActivate: [AuthGuard],
    component: WelcomePanelComponent
  },
  // Default route for non-logged-in users
  { path: 'welcome', component: WelcomePageComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'panel', component: WelcomePanelComponent },
  { path: 'appointment', component: ScheduleAppointmentComponent },
  { path: '**', redirectTo: 'welcome' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
