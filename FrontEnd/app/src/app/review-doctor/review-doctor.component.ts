import { Component } from '@angular/core';
import { ReviewDoctor } from '../services/review/review.model';
import { RoleService } from '../services/role/role.service';
import { ReviewDoctorService} from "../services/review/review.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-review-doctor',
  templateUrl: './review-doctor.component.html',
  styleUrls: ['./review-doctor.component.css']
})
export class ReviewDoctorComponent {
  review: ReviewDoctor = new ReviewDoctor();
  selectedRole: string = 'patient';
  errorMessage: string = '';

  constructor(private reviewService: ReviewDoctorService,
              private roleService: RoleService,
              private router: Router) {}

  ngOnInit(): void {
    this.selectedRole = this.roleService.getSelectedRole();
  }

  submitReviewForm(): void {  
    
    
    const userId = localStorage.getItem('userId');
    if (userId) {
      console.log(this.review)
      this.reviewService.createReview(this.review).subscribe(
        () => {
          // Form submitted successfully
          console.log('Review created successfully');
          // Reset the form
          this.review = new ReviewDoctor();
          this.router.navigate(['/panel']);
        },
        (error) => {
          this.errorMessage = 'Error sending review. Please try again later.';
          console.error('Error creating exam:', error);
        }
      );
    } else {
      this.errorMessage = 'Please log in or authenticate yourself before reviewing a doctor.';
    }
  }
}
