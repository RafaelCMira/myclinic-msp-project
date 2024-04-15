import { Component } from '@angular/core';
import {RoleService} from "../services/role/role.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-hero',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent {
  constructor(private router: Router, public roleService: RoleService) { }

  selectRole(role: string): void {
    this.roleService.setSelectedRole(role);
    console.log(this.roleService.getSelectedRole())
  }

  navigateToLogin(): void {
    this.router.navigate(['/login'])
  }
  navigateToRegister(): void {
    this.router.navigate(['/register'])
  }

}
