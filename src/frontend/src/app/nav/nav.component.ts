import {Component, OnInit} from '@angular/core';
import {ChecklistDatabase, DeleteDialogComponent} from "../sidenav/sidenav.component";
import {CurrentUserService} from "../current-user.service";
import {User} from "../user";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
  providers: [CurrentUserService]
})
export class NavComponent implements OnInit {
  user : User = null;


  constructor(private currentUser: CurrentUserService) {
    //this.user = {
    //  name: "gmy",
    //  role: "STUDENT"
    //}

  }

  ngOnInit() {
  }

  test233() {
    console.log(this.user);
  }

}
