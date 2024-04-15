import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../user/user.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private apiUrl = 'http://localhost:8080/api/v1/doctor';

  constructor(private http: HttpClient) {}

  createDoctor(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, user);
  }
}
