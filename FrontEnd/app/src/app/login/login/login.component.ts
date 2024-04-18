import { Component } from '@angular/core';
import { User } from "../../services/user/user.model";
import { PatientService } from "../../services/patient/patient.service";
import { DoctorService } from "../../services/doctor/doctor.service";
import { RoleService } from "../../services/role/role.service";
import { Router } from "@angular/router";
import { Message, MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: User = new User();
  selectedRole: string;
  messages: Message[] = [];

  constructor(private patientService: PatientService,
              private doctorService: DoctorService,
              private router: Router,
              private roleService: RoleService,
              private messageService: MessageService) {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitForm(): void {
    let loginObservable;
    let type = this.selectedRole;
    if (type === 'patient') {
      loginObservable = this.patientService.loginPatient(this.user);
    } else if (type === 'doctor') {
      loginObservable = this.doctorService.loginDoctor(this.user);
    }

    if (loginObservable) {

      loginObservable.subscribe(
        (userId: number) => { // Receive the user ID upon successful login
          // Form submitted successfully
          console.log(`${type} logged in successfully`);
          // Reset the form
          this.user = new User();
          // Set the logged-in status along with the user ID
          console.log(userId)
          this.roleService.setLoggedInStatus(true, userId);
          this.router.navigate(['/panel']);
        },
        (error) => {
          // Handle error
          console.error(`Error logging in ${type}:`, error);
          this.messages.push({ severity: 'error', summary: 'Error', detail: `Error logging in ${type}: ${error.message}` });
          this.messageService.add({ severity: 'error', summary: 'Error', detail: `Error logging in ${type}: ${error.message}` });
        }
      );
    }
  }

}


// import { Component } from '@angular/core';
// import {User} from "../../services/user/user.model";
// import {PatientService} from "../../services/patient/patient.service";
// import {DoctorService} from "../../services/doctor/doctor.service";
// import {RoleService} from "../../services/role/role.service";
// import {Router} from "@angular/router";
// import {Message, MessageService} from 'primeng/api';
//
// @Component({
//   selector: 'app-login',
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.css']
// })
// export class LoginComponent {
//
//   user: User = new User();
//   selectedRole: string;
//   messages: Message[] = [];
//
//   constructor(private patientService: PatientService,
//               private doctorService: DoctorService,
//               private router: Router,
//               private roleService: RoleService,
//               private messageService: MessageService) {
//     this.selectedRole = this.roleService.getSelectedRole();
//   }
//
//   ngOnInit(): void {
//     this.selectedRole = this.roleService.getSelectedRole();
//     console.log(this.selectedRole)
//   }
//
//
//   submitForm(): void {
//     let loginObservable;
//     let type = this.selectedRole
//     if (type === 'patient') {
//       loginObservable = this.patientService.loginPatient(this.user);
//     } else if (type === 'doctor') {
//       loginObservable = this.doctorService.loginDoctor(this.user);
//     }
//
//     if (loginObservable) {
//       loginObservable.subscribe(
//         () => {
//           // Form submitted successfully
//           console.log(`${type} logged in successfully`);
//           // Reset the form
//           this.user = new User();
//           this.roleService.setLoggedInStatus(true);
//           this.router.navigate(['/panel']);
//         },
//         (error) => {
//           // Handle error
//           console.error(`Error logging in ${type}:`, error);
//           this.messages.push({ severity: 'error', summary: 'Error', detail: `Error logging in ${type}: ${error.message}` });
//           this.messageService.add({severity:'error', summary:'Error', detail:`Error logging in ${type}: ${error.message}`});
//         }
//       );
//     }
//   }
//
// }
