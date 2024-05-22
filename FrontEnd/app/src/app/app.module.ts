import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { FooterComponent } from './footer/footer.component';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule} from "primeng/button";
import { RegisterComponent } from './register/register.component';
import {FormsModule} from "@angular/forms";
import {CalendarModule} from "primeng/calendar";
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { LoginComponent } from './login/login/login.component';
import { WelcomePanelComponent } from './welcome-panel/welcome-panel.component';
import {PanelModule} from "primeng/panel";
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';
import { ScheduleAppointmentComponent} from "./schedule-appointment/schedule-appointment.component";
import { ScheduleExamComponent} from "./schedule-exam/schedule-exam.component";
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import {MessageModule} from "primeng/message";
import {ToastModule} from "primeng/toast";
import { DoctorListComponent } from './doctor-list/doctor-list.component';
import {Table, TableModule} from "primeng/table";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    WelcomePageComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    WelcomePanelComponent,
    ScheduleAppointmentComponent,
    ScheduleExamComponent,
    DoctorListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    ButtonModule,
    FormsModule,
    CalendarModule,
    HttpClientModule,
    InputTextModule,
    BrowserAnimationsModule,
    PanelModule,
    MenubarModule,
    MessagesModule,
    MessageModule,
    CalendarModule,
    DropdownModule,
    FileUploadModule,
    ToastModule,
    TableModule,


  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
