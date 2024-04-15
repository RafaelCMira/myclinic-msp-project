import { Component } from '@angular/core';
import {RoleService} from "../services/role/role.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

    selectedRole: string = 'patient';

    noneMenuItems: any[] = [
        { label: 'Home', routerLink: '/' },
        { label: 'About', routerLink: '/about' },
    ];

    patientMenuItems: any[] = [
        { label: 'Home', routerLink: '/' },
        { label: 'About', routerLink: '/about' },
        { label: 'Appointments', routerLink: '/appointment' },
        { label: 'Prescriptions', routerLink: '/prescription' },
    ];

    doctorMenuItems: any[] = [
        { label: 'Home', routerLink: '/' },
        { label: 'About', routerLink: '/about' },
        { label: 'Appointments', routerLink: '/appointment' },
    ];

    constructor(private roleService: RoleService) {

    }

    ngOnInit(): void {
      this.selectedRole = this.roleService.getSelectedRole();
    }
}
