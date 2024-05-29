import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Appointment} from "./clinics.model";

@Injectable({
  providedIn: 'root'
})
export class ClinicsService {

  private apiUrl = 'http://localhost:8080/api/v1/clinic';

  constructor(private http: HttpClient) {}

  getClinics(): Observable<any> {
    return this.http.get<Appointment[]>(`${this.apiUrl}`);
  }
}
