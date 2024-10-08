import { Component } from '@angular/core';
import {RoleService} from "../services/role/role.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

    selectedRole: string;

    noneMenuItems: any[] = [
        { label: 'Home', routerLink: '/' },
        { label: 'About', routerLink: '/about' },
    ];

    patientMenuItems: any[] = [
        { label: 'Panel', routerLink: '/panel' },
        { label: 'Appointments', routerLink: '/appointments' },
        { label: 'Exams', routerLink: '/exam' },
        { label: 'Doctors', routerLink: '/doctor' },

    ];

    doctorMenuItems: any[] = [
        { label: 'Panel', routerLink: '/panel' },
        { label: 'Appointments', routerLink: '/appointments' },
        { label: 'Exams', routerLink: '/exam' },
    ];

    constructor(private roleService: RoleService) {
      this.selectedRole = this.roleService.getSelectedRole();
      // this.roleService.selectedRoleChanged.subscribe(role => {
      //   this.selectedRole = role;
      // });
    }

    ngOnInit(): void {
      this.updateSelectedRole();
      // Subscribe to changes in the selected role
      this.roleService.selectedRoleChanged.subscribe(() => {
        this.updateSelectedRole();
      });
      // Subscribe to changes in login status
      this.roleService.loggedInStatusChanged.subscribe(() => {
        this.updateSelectedRole();
      });
    }

    updateSelectedRole(): void {
      if (this.roleService.isLoggedIn()) {
        this.selectedRole = this.roleService.getSelectedRole();
      } else {
        this.selectedRole = 'none';
      }
    }
}
