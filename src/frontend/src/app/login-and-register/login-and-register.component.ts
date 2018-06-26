import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import {Location} from '@angular/common';

@Component({
  selector: 'app-login-and-register',
  templateUrl: './login-and-register.component.html',
  styleUrls: ['./login-and-register.component.css']
})
export class LoginAndRegisterComponent implements OnInit {

  tabIndex: number = 0;

  constructor(private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit() {
    this.route.url.subscribe(url => {
      this.tabIndex = url[0].path == 'login' ? 0 : 1;
    });
  }

  indexChange(index) {
    this.location.go(index == 0 ? '/app/login' : '/app/register')
  }

}
