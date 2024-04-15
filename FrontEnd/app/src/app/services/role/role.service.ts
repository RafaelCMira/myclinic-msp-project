import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  static selectedRole: string;

  constructor() {
    RoleService.selectedRole = 'none'
  }

  setSelectedRole(role: string): void {
    RoleService.selectedRole = role;
  }

  getSelectedRole(): string {
    return RoleService.selectedRole;
  }
}
