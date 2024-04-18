import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Appointment} from "./appointment.model";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointment';

  constructor(private http: HttpClient) {}

  createAppointment(appointment: Appointment): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, appointment);
  }

}
