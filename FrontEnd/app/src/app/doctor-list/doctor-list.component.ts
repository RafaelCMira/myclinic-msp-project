// src/app/components/doctor-list/doctor-list.component.ts
import { Component, OnInit } from '@angular/core';
import { DoctorService } from "../services/doctor/doctor.service";
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

  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
    this.loadMockSpecialities();
    this.loadMockClinics();
    this.loadDoctors();
  }

  loadMockSpecialities(): void {
    this.specialities = [
      { label: 'All Specialities', value: null },
      { label: 'Cardiologist', value: 1 },
      { label: 'Dermatologist', value: 2 },
      { label: 'Endocrinologist', value: 3 },
      { label: 'Gastroenterologist', value: 4 },
      { label: 'Neurologist', value: 5 },
      { label: 'Oncologist', value: 6 },
      { label: 'Pediatrician', value: 7 },
      { label: 'Psychiatrist', value: 8 }
      // Add more mock specialities as needed
    ];
  }

  loadMockClinics(): void {
    this.clinics = [
      { label: 'All Clinics', value: null },
      { label: 'Clinic Viana do Castelo', value: 1 },
      { label: 'Clinic Évora', value: 2 },
      { label: 'Clinic Leiria', value: 3 },
      { label: 'Clinic Portalegre', value: 4 },
      { label: 'Clinic Faro', value: 5 }
      // Add more mock clinics as needed
    ];
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
