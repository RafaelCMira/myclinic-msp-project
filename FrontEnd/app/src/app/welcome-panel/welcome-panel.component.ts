import { Component, OnInit } from '@angular/core';
import { Appointment} from "../services/appointment/appointment.model";
import { AppointmentService} from "../services/appointment/appointment.service";
import {RoleService} from "../services/role/role.service";
import {Message, MessageService} from "primeng/api";

@Component({
  selector: 'app-welcome-panel',
  templateUrl: './welcome-panel.component.html',
  styleUrls: ['./welcome-panel.component.css']
})
export class WelcomePanelComponent implements OnInit {
  appointments: Appointment[] = [];
  messages: Message[] = [];

  constructor(private appointmentService: AppointmentService,
              private roleService: RoleService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadAppointments();
    this.messages = [{severity:'success',
                      summary:'Check-in Successful',
                      detail:'You have successfully checked in for your appointment.'}];
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
}
