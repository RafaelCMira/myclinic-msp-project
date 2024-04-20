import { Component } from '@angular/core';
import { Appointment } from '../services/appointment/appointment.model';
import { RoleService } from '../services/role/role.service';
import { AppointmentService} from "../services/appointment/appointment.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.css']
})
export class ScheduleAppointmentComponent {
  appointment: Appointment = new Appointment();
  selectedRole: string = 'patient';
  errorMessage: string = '';

  clinics = [
    { id: '1', label: 'West Clinic', value: 'wc' },
    { id: '2', label: 'South Clinic', value: 'sc' }
  ];

  doctors = [
    { id: '1', label: 'Ms. Hai Haag', value: 'd1' },
    { id: '2', label: 'Gillian Padberg III', value: 'd2' },
    { id: '3', label: 'Dottie Shields I', value: 'd3' },
    { id: '4', label: 'Miguel Sporer', value: 'd4' },
    { id: '5', label: 'Sherwood Ebert', value: 'd5' }
  ];

  durations = [
    { id: '1', label: '15 Minutes', value: '15' },
    { id: '2', label: '30 Minutes', value: '30' },
    { id: '3', label: '45 Minutes', value: '45' },
    { id: '4', label: '60 Minutes', value: '60' },
  ];

  doctorId: number | undefined;
  clinicId: number | undefined;
  duration: number | undefined;

  constructor(private appointmentService: AppointmentService,
              private roleService: RoleService,
              private router: Router) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    const date = new Date(this.appointment.date);

    if (isNaN(date.getTime()) || date < new Date()) {
      this.errorMessage = 'Appointment date must be valid and at least from the next day.';
      return;
    }
  
    const maxDate = new Date();
    maxDate.setMonth(maxDate.getMonth() + 2);
    if (date > maxDate) {
      this.errorMessage = 'Appointment date must be within 2 months from today.';
      return;
    }

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based, so add 1
    const day = date.getDate().toString().padStart(2, '0');
    this.appointment.date = `${year}-${month}-${day}`;

    const durationHours = Math.floor(this.duration! / 60);
    const durationMinutes = this.duration! % 60;
    const formattedDuration = `${durationHours.toString().padStart(2, '0')}:${durationMinutes.toString().padStart(2, '0')}:00`;
    this.appointment.duration = formattedDuration;

    if (this.clinicId !== undefined) {
      this.appointment.clinicId = +this.clinicId;
    } else {
      this.errorMessage = 'Please select a clinic.';
      return;
    }

    if (this.doctorId !== undefined) {
      this.appointment.doctorId = +this.doctorId;
    } else {
      this.errorMessage = 'Please select a doctor.';
      return;
    }

    const userId = localStorage.getItem('userId');

    if (userId) {
      this.appointment.patientId = userId;
      console.log(this.appointment)
      this.appointmentService.createAppointment(this.appointment).subscribe(
        () => {
          // Form submitted successfully
          console.log('Appointment created successfully');
          // Reset the form
          this.appointment = new Appointment();
          this.router.navigate(['/panel']);
        },
        (error) => {
          this.errorMessage = 'Error creating appointment. Please try again later.';
          console.error('Error creating appointment:', error);
        }
      );
    } else {
      this.errorMessage = 'Please log in or authenticate yourself before scheduling an appointment.';
    }
  }
}
