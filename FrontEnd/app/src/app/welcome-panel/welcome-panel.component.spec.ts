import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomePanelComponent } from './welcome-panel.component';

describe('WelcomePanelComponent', () => {
  let component: WelcomePanelComponent;
  let fixture: ComponentFixture<WelcomePanelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WelcomePanelComponent]
    });
    fixture = TestBed.createComponent(WelcomePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
