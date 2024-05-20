import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ReviewDoctor} from "./review.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewDoctorService {

  private apiUrl = 'http://localhost:8080/api/v1/appointment';

  constructor(private http: HttpClient) {}

  createReview(review: ReviewDoctor): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, review);
  }

  getReviews(doctorId: number): Observable<any> {
    return this.http.get<ReviewDoctor[]>(`${this.apiUrl}?doctorId=${doctorId}`);
  }
}
