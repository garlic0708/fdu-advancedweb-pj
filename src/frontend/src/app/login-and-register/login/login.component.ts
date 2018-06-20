import {Component, OnInit} from '@angular/core';
import { CurrentUserService } from "../../current-user.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  error: boolean = false;
  submitting: boolean;

  constructor(private currentUser: CurrentUserService,
              private router: Router) {
    this.email = "";
    this.password = "";
  }

  ngOnInit() {
  }


  onSubmit() {
    this.submitting = true;
    this.currentUser.login(this.email, this.password)
      .subscribe(() => {
        this.submitting = false;
        this.router.navigateByUrl('/app')
      }, (x) => {
        console.log(x);
        this.submitting = false;
        this.error = true;
      })
  }

  valid() {
    return !(this.email == "" || this.password == "");
  }

}
