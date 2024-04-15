import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../user/user.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private apiUrl = 'http://localhost:8080/api/v1/patient';

  constructor(private http: HttpClient) {}

  createPatient(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, user);
  }
}
