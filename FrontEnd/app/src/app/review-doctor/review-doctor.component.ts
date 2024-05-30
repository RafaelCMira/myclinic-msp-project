import { Component } from '@angular/core';
import { ReviewDoctor } from '../services/review/review.model';
import { RoleService } from '../services/role/role.service';
import { ReviewDoctorService} from "../services/review/review.service";
import { Router } from "@angular/router";
import { AppointmentService} from "../services/appointment/appointment.service";
import { ActivatedRoute } from '@angular/router';
import { Appointment } from '../services/appointment/appointment.model';

@Component({
  selector: 'app-review-doctor',
  templateUrl: './review-doctor.component.html',
  styleUrls: ['./review-doctor.component.css']
})
export class ReviewDoctorComponent {
  review: ReviewDoctor = new ReviewDoctor();
  selectedRole: string = 'patient';
  errorMessage: string = '';
  appointment: any;

  constructor(private reviewService: ReviewDoctorService,
              private appointmentService: AppointmentService,
              private roleService: RoleService,
              private router: Router,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
    const navigation = this.router.getCurrentNavigation();
    this.route.queryParams.subscribe(params => {
      this.appointment = { ...params };
      console.log(this.appointment)
    });
  }

  submitReviewForm(): void {  
    const userId = localStorage.getItem('userId');
    
    if (userId) {
        this.appointment.rating = this.review.rating;
        this.appointment.review = this.review.opinion;
        console.log(this.appointment)
        this.appointmentService.updateAppointment(this.appointment).subscribe(
            () => {
                console.log('Appointment updated successfully with review.');
                this.router.navigate(['/appointments']);
            },
            (error) => {
                this.errorMessage = 'Error updating appointment. Please try again later.';
                console.error('Error updating appointment:', error);
            }
        );
    } else {
        this.errorMessage = 'Please log in or authenticate yourself before reviewing a doctor.';
    }
}
}
