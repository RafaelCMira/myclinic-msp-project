import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Exam} from "../exam/exam.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  private apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  createExam(exam: Exam): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/patient`, exam);
  }
}
