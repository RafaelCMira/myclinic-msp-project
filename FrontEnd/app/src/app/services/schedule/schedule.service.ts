import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Appointment} from "../appointment/appointment.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  createAppointment(appointment: Appointment): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/patient`, appointment);
  }
}