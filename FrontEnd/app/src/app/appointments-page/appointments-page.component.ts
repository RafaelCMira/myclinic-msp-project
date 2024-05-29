import { Component, OnInit } from '@angular/core';
import { Appointment } from '../services/appointment/appointment.model';
import { RoleService } from '../services/role/role.service';
import { AppointmentService} from "../services/appointment/appointment.service";
import { Router } from "@angular/router";
import {Message, MessageService} from "primeng/api";

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments-page.component.html',
  styleUrls: ['./appointments-page.component.css']
})
export class AppointmentsComponent implements OnInit {
  appointments: Appointment[] = [];
  messages: Message[] = [];

  constructor(private appointmentService: AppointmentService,
              private roleService: RoleService,
              private router: Router,
              private messageService: MessageService) {}

  ngOnInit(): void {
    this.loadAppointments();
    this.messages = [{severity:'success',
                      summary:'Check-in Successful',
                      detail:'You have successfully checked in for your appointment.'}];
  }

  navigateToScheduleAppointment() {
    this.router.navigate(['/schedule-appointment']);
  }

  loadAppointments(): void {
    const userId = +this.roleService.getLoggedInUserId();
    this.appointmentService.getAppointments(userId).subscribe(
      response => {
        if (response && response.status === 'SUCCESS' && response.result) {
          this.appointments = response.result;
        } else {
          console.error('Error fetching appointments:', response);
        }
      },
      error => {
        console.error('Error fetching appointments:', error);
      }
    );
 }

 checkIn(appointment: Appointment): void {
   this.messageService.add({
     severity: 'success', summary: 'Success',
     detail: 'You have successfully checked in for your appointment.'
   });
 }

 review(appointment: Appointment): void {
  this.router.navigate(['/review'], { queryParams: appointment });
}
}
