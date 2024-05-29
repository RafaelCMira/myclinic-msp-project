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
import { ScheduleExamComponent } from "./schedule-exam/schedule-exam.component";
import { LoginComponent } from "./login/login/login.component";
import { WelcomePanelComponent } from "./welcome-panel/welcome-panel.component";
import { ReviewDoctorComponent } from "./review-doctor/review-doctor.component";
import { RoleService } from "./services/role/role.service";
import {Observable} from "rxjs";
import {DoctorListComponent} from "./doctor-list/doctor-list.component";
import {AppointmentsComponent} from "./appointments-page/appointments-page.component";



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
  { path: '', canActivate: [AuthGuard], component: WelcomePanelComponent },
  { path: 'schedule-appointment', canActivate: [AuthGuard], component: ScheduleAppointmentComponent },
  { path: 'appointments', canActivate: [AuthGuard], component: AppointmentsComponent },
  { path: 'exam', canActivate: [AuthGuard], component: ScheduleExamComponent },
  { path: 'doctor', canActivate: [AuthGuard], component: DoctorListComponent },

  // Default route for non-logged-in users
  { path: 'welcome', component: WelcomePageComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'panel', component: WelcomePanelComponent },
  { path: 'review', component: ReviewDoctorComponent },
  { path: '**', redirectTo: 'welcome' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
