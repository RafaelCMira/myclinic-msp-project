import { Component } from '@angular/core';
import { Appointment} from "../services/appointment/appointment.model";
import {RoleService} from "../services/role/role.service";
import {ScheduleService} from "../services/schedule/schedule.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.css']
})
export class ScheduleAppointmentComponent {
  appointment: Appointment = new Appointment();
  selectedRole: string = 'patient';

  constructor(private scheduleService: ScheduleService,
              private router: Router,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    this.scheduleService.createAppointment(this.appointment).subscribe(
      () => {
        // Form submitted successfully
        console.log('Patient created successfully');
        // Reset the form
        this.appointment = new Appointment();
        //this.router.navigate(['/login'])
      },
      (error) => {
        // Handle error
        console.error('Error creating Patient:', error);
      }
    );
  }
}