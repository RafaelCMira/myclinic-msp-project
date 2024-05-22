import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../user/user.model";
import {Observable} from "rxjs";
import {Doctor} from "./doctor.model";
import {ApiResponse} from "./api-repsonse";

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  createDoctor(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/doctor`, user);
  }

  loginDoctor(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, user);
  }

  getDoctors(speciality?: number, clinic?: number): Observable<ApiResponse<Doctor[]>> {
    let url = this.apiUrl + '/doctor'
    url = `${url}?`;

    if (speciality !== null && speciality !== undefined) {
      url += `speciality=${speciality}&`;
    }
    if (clinic !== null && clinic !== undefined) {
      url += `clinic=${clinic}`;
    }

    // Remove any trailing '&' or '?' from the URL
    url = url.replace(/[&?]$/, '');

    return this.http.get<ApiResponse<Doctor[]>>(url);
  }
}
