import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Appointment} from "./specialities.model";

@Injectable({
  providedIn: 'root'
})
export class SpecialitiesService {

  private apiUrl = 'http://localhost:8080/api/v1/speciality';

  constructor(private http: HttpClient) {}

  getSpecialities(): Observable<any> {
    return this.http.get<Appointment[]>(`${this.apiUrl}`);
  }
}
