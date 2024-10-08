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
  errorMessage: string = '';

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
    console.log(type)

    if (type === 'patient') {
      loginObservable = this.patientService.loginPatient(this.user);
    } else if (type === 'doctor') {
      loginObservable = this.doctorService.loginDoctor(this.user);
    }

    if (loginObservable) {
      loginObservable.subscribe(
        (response: any) => {
          // Check if the response contains the user ID attribute
          if (response && response.result && response.result.id) {
            console.log(`${type} logged in successfully`);
            this.user = new User();
            // Access the user ID from the response object
            const userId = response.result.id;
            console.log(userId);
            this.roleService.setLoggedInStatus(true, userId);
            // Save the user ID in local storage
            localStorage.setItem('userId', userId);
            this.router.navigate(['/panel']);
          } else {
            console.error(`Error logging in ${type}: Response does not contain user ID`);
            // Handle error
          }
        },
        (error) => {
          console.error(`Error logging in ${type}:`, error);
          this.errorMessage = 'Error logging in. Please try again later.';
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
