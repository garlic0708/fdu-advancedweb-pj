import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginAndRegisterComponent } from './login-and-register.component';
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { MaterialModule } from "../material/material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { LoginRoutingModule } from "./login-routing/login-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    LoginRoutingModule,
  ],
  declarations: [
    LoginAndRegisterComponent,
    LoginComponent,
    RegisterComponent,
  ],
})
export class LoginAndRegisterModule {
}
