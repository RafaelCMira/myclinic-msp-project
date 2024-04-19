import { Component } from '@angular/core';
import { Exam } from '../services/exam/exam.model';
import { RoleService } from '../services/role/role.service';
import { ExamService} from "../services/exam/exam.service";

@Component({
  selector: 'app-schedule-exam',
  templateUrl: './schedule-exam.component.html',
  styleUrls: ['./schedule-exam.component.css']
})
export class ScheduleExamComponent {
  exam: Exam = new Exam();
  selectedRole: string = 'patient';
  errorMessage: string = '';

  constructor(private examService: ExamService,
              private roleService: RoleService) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitScheduleForm(): void {
    const date = new Date(this.exam.date);

    if (isNaN(date.getTime()) || date < new Date()) {
      this.errorMessage = 'Exam date must be valid and at least from the current day.';
      return;
    }
  
    const maxDate = new Date();
    maxDate.setMonth(maxDate.getMonth() + 2);
    if (date > maxDate) {
      this.errorMessage = 'Exam date must be within 2 months from today.';
      return;
    }
  
    if (date.getMinutes() < 15) {
      this.errorMessage = 'Exam must be scheduled at least 15 minutes from now.';
      return;
    }

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based, so add 1
    const day = date.getDate().toString().padStart(2, '0');
    this.exam.date = `${year}-${month}-${day}`;

    const userId = localStorage.getItem('userId');
    if (userId) {
      this.exam.patientId = userId;
      console.log(this.exam.date)
      this.examService.createExam(this.exam).subscribe(
        () => {
          // Form submitted successfully
          console.log('Exam created successfully');
          // Reset the form
          this.exam = new Exam();
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
