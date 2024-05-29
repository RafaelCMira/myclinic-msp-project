import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsComponent } from './appointments-page.component';

describe('AppointmentsComponent', () => {
  let component: AppointmentsComponent;
  let fixture: ComponentFixture<AppointmentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppointmentsComponent]
    });
    fixture = TestBed.createComponent(AppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
