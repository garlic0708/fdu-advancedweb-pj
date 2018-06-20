import {Component, OnInit} from '@angular/core';
import {CurrentUserService} from "../current-user.service";
import {User} from "../user";
import { Router } from "@angular/router";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  user : User = null;


  constructor(private currentUser: CurrentUserService,
              private router: Router) {
    //this.user = {
    //  name: "gmy",
    //  role: "STUDENT"
    //}
  }

  ngOnInit() {
    this.currentUser.subscribe(user => {
      console.log(user);
      this.user = user;
    })
  }

  logout() {
    this.currentUser.logout()
      .subscribe(() => this.router.navigate(['/login']))
  }

}
