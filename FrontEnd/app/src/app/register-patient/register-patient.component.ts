import { Component } from '@angular/core';
import { User} from "../services/user/user.model";
import {RoleService} from "../services/role/role.service";
import {PatientService} from "../services/patient/patient.service";
import {DoctorService} from "../services/doctor/doctor.service";

@Component({
  selector: 'app-register-patient',
  templateUrl: './register-patient.component.html',
  styleUrls: ['./register-patient.component.css']
})
export class RegisterPatientComponent {
  user: User = new User();
  selectedRole: string = 'patient';

  constructor(private patientService: PatientService,
              private doctorService: DoctorService,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitPatientForm(): void {
    this.patientService.createPatient(this.user).subscribe(
      () => {
        // Form submitted successfully
        console.log('Patient created successfully');
        // Reset the form
        this.user = new User();
      },
      (error) => {
        // Handle error
        console.error('Error creating Patient:', error);
      }
    );
  }

  submitDoctorForm(): void {
    this.doctorService.createDoctor(this.user).subscribe(
      () => {
        // Form submitted successfully
        console.log('Doctor created successfully');
        // Reset the form
        this.user = new User();
      },
      (error) => {
        // Handle error
        console.error('Error creating doctor:', error);
      }
    );
  }
}
