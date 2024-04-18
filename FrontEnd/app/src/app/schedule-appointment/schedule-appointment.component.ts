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

  constructor(private appointmentService: AppointmentService,
              private router: Router,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    this.appointment.hour += ':00.000';
    this.appointment.duration += ':00.000';
    console.log(this.appointment.duration)
    this.appointmentService.createAppointment(this.appointment).subscribe(
      () => {
        // Form submitted successfully
        console.log('Appointment created successfully');
        // Reset the form
        this.appointment = new Appointment();
        // Optionally navigate to another page after form submission
        // this.router.navigate(['/login'])
      },
      (error) => {
        // Handle error
        console.error('Error creating appointment:', error);
      }
    );
  }
}
