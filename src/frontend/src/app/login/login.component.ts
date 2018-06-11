import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  user: string;

  constructor() {
    this.email = "";
    this.password = "";
    this.user = "";
  }

  ngOnInit() {
  }


  onSubmit() {
  }

  valid() {
    if (this.email == "" || this.password == "" || this.user == "") return true;
    else return false;
  }

  test233() {
    console.log(this.user);
  }

}
