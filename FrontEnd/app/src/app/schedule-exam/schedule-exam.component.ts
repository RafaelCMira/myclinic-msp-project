import { Component } from '@angular/core';
import { Exam } from '../services/exam/exam.model';
import { RoleService } from '../services/role/role.service';
import { ExamService} from "../services/exam/exam.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-schedule-exam',
  templateUrl: './schedule-exam.component.html',
  styleUrls: ['./schedule-exam.component.css']
})
export class ScheduleExamComponent {
  exam: Exam = new Exam();
  selectedRole: string = 'patient';
  errorMessage: string = '';

  clinics = [
    { id: '1', label: 'West Clinic', value: 'wc' },
    { id: '2', label: 'South Clinic', value: 'sc' }
  ];

  equipments = [
    { id: '1', label: 'Electrocardiogram', value: 'd1' },
    { id: '2', label: 'X-ray machine', value: 'd2' },
    { id: '3', label: 'Ultrasound machine', value: 'd3' },
    { id: '4', label: 'MRI machine', value: 'd4' },
    { id: '5', label: 'CT scanner', value: 'd5' },
    { id: '6', label: 'Blood pressure monitor', value: 'd6' },
    { id: '7', label: 'Stethoscope', value: 'd7' },
    { id: '8', label: 'Thermometer', value: 'd8' }
  ];

  equipmentId: number | undefined;
  clinicId: number | undefined;

  constructor(private examService: ExamService,
              private roleService: RoleService,
              private router: Router) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    const date = new Date(this.exam.date);

    if (isNaN(date.getTime()) || date < new Date()) {
      this.errorMessage = 'Exam date must be valid and at least from the next day.';
      return;
    }
  
    const maxDate = new Date();
    maxDate.setMonth(maxDate.getMonth() + 2);
    if (date > maxDate) {
      this.errorMessage = 'Exam date must be within 2 months from today.';
      return;
    }
  
    if (date.getMinutes() > 15) {
      this.errorMessage = 'Exam must be scheduled at least 15 minutes.';
      return;
    }

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based, so add 1
    const day = date.getDate().toString().padStart(2, '0');
    this.exam.date = `${year}-${month}-${day}`;

    if (this.clinicId !== undefined) {
      this.exam.clinicId = +this.clinicId;
    } else {
      this.errorMessage = 'Please select a clinic.';
      return;
    }

    if (this.equipmentId !== undefined) {
      this.exam.equipmentId = +this.equipmentId;
    } else {
      this.errorMessage = 'Please select a doctor.';
      return;
    }
    
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.exam.patientId = userId;
      console.log(this.exam)
      this.examService.createExam(this.exam).subscribe(
        () => {
          // Form submitted successfully
          console.log('Exam created successfully');
          // Reset the form
          this.exam = new Exam();
          this.router.navigate(['/panel']);
        },
        (error) => {
          this.errorMessage = 'Error creating appointment. Please try again later.';
          console.error('Error creating exam:', error);
        }
      );
    } else {
      this.errorMessage = 'Please log in or authenticate yourself before scheduling an exam.';
    }
  }
}
