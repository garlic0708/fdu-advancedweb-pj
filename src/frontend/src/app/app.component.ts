import { Component, Injectable, ViewChild } from '@angular/core';
import { CurrentUserService } from "./current-user.service";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor() {
  }
}
