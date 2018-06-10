import { Component } from '@angular/core';
import { CurrentUserService } from "./current-user.service";
import { catchError } from "rxjs/internal/operators";
import { Observable } from "rxjs/index";
import { getOrError } from "./http-util";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private service: CurrentUserService) {
  }

  title = 'app';

  log(e): void {
    console.log(e)
  }

  test() {
    getOrError(() => this.service.login("a@best.com", 2),
      data => console.log(data),
      err => console.log(err))
  }
}

