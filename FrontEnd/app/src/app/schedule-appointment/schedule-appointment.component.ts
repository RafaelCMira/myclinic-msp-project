import { Component } from '@angular/core';
import { Appointment } from '../services/appointment/appointment.model';
import { RoleService } from '../services/role/role.service';
import { AppointmentService} from "../services/appointment/appointment.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.css']
})
export class ScheduleAppointmentComponent {
  appointment: Appointment = new Appointment();
  selectedRole: string = 'patient';
  errorMessage: string = '';

  constructor(private appointmentService: AppointmentService,
              private router: Router,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    const date = new Date(this.appointment.date);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based, so add 1
    const day = date.getDate().toString().padStart(2, '0');
    this.appointment.date = `${year}-${month}-${day}`;

    const userId = localStorage.getItem('userId');
    if (userId) {
      this.appointment.patientId = userId;
      console.log(this.appointment.date)
      this.appointmentService.createAppointment(this.appointment).subscribe(
        () => {
          // Form submitted successfully
          console.log('Appointment created successfully');
          // Reset the form
          this.appointment = new Appointment();
        },
        (error) => {
          // Handle error
          console.error('Error creating appointment:', error);
        }
      );
    } else {
      this.errorMessage = 'Please log in or authenticate yourself before scheduling an appointment.';
    }
  }
}
