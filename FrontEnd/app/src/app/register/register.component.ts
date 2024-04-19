import { Component } from '@angular/core';
import { User} from "../services/user/user.model";
import {RoleService} from "../services/role/role.service";
import {PatientService} from "../services/patient/patient.service";
import {DoctorService} from "../services/doctor/doctor.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: User = new User();
  selectedRole: string = 'patient';
  errorMessage: string = '';
  private phoneRegex: RegExp = /^\d{9}$/;

  constructor(private patientService: PatientService,
              private doctorService: DoctorService,
              private router: Router,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitPatientForm(): void {
    if (!this.phoneRegex.test(this.user.phone)) {
      this.errorMessage = 'Phone number should be 9 digits.';
      return;
    }

    this.patientService.createPatient(this.user).subscribe(
      () => {
        // Form submitted successfully
        console.log('Patient created successfully');
        // Reset the form
        this.user = new User();
        this.router.navigate(['/login'])
      },
      (error) => {
        this.errorMessage = 'Error creating Patient. Please try again later.';
        console.error('Error creating Patient:', error);
      }
    );
  }

  submitDoctorForm(): void {

    if (!this.phoneRegex.test(this.user.phone)) {
      this.errorMessage = 'Phone number should be 9 digits.';
      return;
    }

    this.doctorService.createDoctor(this.user).subscribe(
      () => {
        // Form submitted successfully
        console.log('Doctor created successfully');
        // Reset the form
        this.user = new User();
        this.router.navigate(['/login'])
      },
      (error) => {
        this.errorMessage = 'Error creating Patient. Please try again later.';
        console.error('Error creating doctor:', error);
      }
    );
  }
}
