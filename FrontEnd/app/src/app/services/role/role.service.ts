import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private selectedRole: string;
  private loggedIn: boolean;
  private loggedInUserId!: number; // Store the logged-in user's ID

  // Subject to notify subscribers when the selected role changes
  selectedRoleChanged: Subject<string> = new Subject<string>();
  // Subject to notify subscribers when the login status changes
  loggedInStatusChanged: Subject<boolean> = new Subject<boolean>();

  constructor() {
    this.selectedRole = 'none';
    this.loggedIn = false;
  }

  setSelectedRole(role: string): void {
    this.selectedRole = role;
    // Notify subscribers about the change in selected role
    this.selectedRoleChanged.next(this.selectedRole);
  }

  getSelectedRole(): string {
    return this.selectedRole;
  }

  getUserId(email: string, password: string): Observable<number> {
    // Make an HTTP request to your backend to retrieve the user ID
    return this.http.post<number>(`${this.apiUrl}/user-id`, { email, password });
  }
  setLoggedInStatus(status: boolean, userId: number): void {
    this.loggedIn = status;
    this.loggedInUserId = userId; // Store the logged-in user's ID
    // Notify subscribers about the change in login status
    this.loggedInStatusChanged.next(this.loggedIn);
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  getLoggedInUserId(): number {
    return this.loggedInUserId; // Return the logged-in user's ID
  }
}


// import { Injectable } from '@angular/core';
// import { Subject } from 'rxjs';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class RoleService {
//   private selectedRole: string;
//   private loggedIn: boolean;
//
//   // Subject to notify subscribers when the selected role changes
//   selectedRoleChanged: Subject<string> = new Subject<string>();
//   // Subject to notify subscribers when the login status changes
//   loggedInStatusChanged: Subject<boolean> = new Subject<boolean>();
//
//   constructor() {
//     this.selectedRole = 'none';
//     this.loggedIn = false;
//   }
//
//   setSelectedRole(role: string): void {
//     this.selectedRole = role;
//     // Notify subscribers about the change in selected role
//     this.selectedRoleChanged.next(this.selectedRole);
//   }
//
//   getSelectedRole(): string {
//     return this.selectedRole;
//   }
//
//   setLoggedInStatus(status: boolean): void {
//     this.loggedIn = status;
//     // Notify subscribers about the change in login status
//     this.loggedInStatusChanged.next(this.loggedIn);
//   }
//
//   isLoggedIn(): boolean {
//     return this.loggedIn;
//   }
// }
