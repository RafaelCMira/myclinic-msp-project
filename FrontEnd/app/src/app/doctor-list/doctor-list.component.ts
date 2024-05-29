// src/app/components/doctor-list/doctor-list.component.ts
import { Component, OnInit } from '@angular/core';
import { DoctorService } from "../services/doctor/doctor.service";
import { ClinicsService } from "../services/clinics/clinics.service";
import { SpecialitiesService } from "../services/specialities/specialities.service";
import { Doctor } from "../services/doctor/doctor.model";
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit {
  doctors: Doctor[] = [];
  specialities: SelectItem[] = [];
  clinics: SelectItem[] = [];
  selectedSpeciality: number | null = null;
  selectedClinic: number | null = null;

  constructor(private doctorService: DoctorService,
              private clinicsService: ClinicsService,
              private specialitiesService: SpecialitiesService
  ) { }

  ngOnInit(): void {
    this.loadSpecialities();
    this.loadClinics();
    this.loadDoctors();
  }

  loadSpecialities(): void {
    this.specialitiesService.getSpecialities().subscribe(
      response => {
        if (response.status === 'SUCCESS' && Array.isArray(response.result)) {
          // Start with an 'All Clinics' option
          const specialitiesArray = [{ label: 'All Specialitites', value: null }];
          
          // Transform the response.result data to match the desired format
          response.result.forEach((speciality: any) => {
            specialitiesArray.push({
              label: `${speciality.name}`,
              value: speciality.id
            });
          });

          // Assign the transformed data to `this.clinics`
          this.specialities = specialitiesArray;
        } else {
          console.error('Unexpected response structure:', response);
        }
      },
      error => {
        console.error('Error fetching specialities:', error);
      }
    );
  }

  loadClinics(): void {
    this.clinicsService.getClinics().subscribe(
      response => {
        if (response.status === 'SUCCESS' && Array.isArray(response.result)) {
          // Start with an 'All Clinics' option
          const clinicsArray = [{ label: 'All Clinics', value: null }];
          
          // Transform the response.result data to match the desired format
          response.result.forEach((clinic: any) => {
            clinicsArray.push({
              label: `${clinic.name}`,
              value: clinic.id
            });
          });

          // Assign the transformed data to `this.clinics`
          this.clinics = clinicsArray;
        } else {
          console.error('Unexpected response structure:', response);
        }
      },
      error => {
        console.error('Error fetching clinics:', error);
      }
    );
  }

  loadDoctors(): void {
    this.doctorService.getDoctors(
      this.selectedSpeciality !== null ? this.selectedSpeciality : undefined,
      this.selectedClinic !== null ? this.selectedClinic : undefined
    ).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.doctors = response.result;
        console.log(this.doctors)
      } else {
        // Handle error or empty result
        this.doctors = [];
      }
    });
  }
}
