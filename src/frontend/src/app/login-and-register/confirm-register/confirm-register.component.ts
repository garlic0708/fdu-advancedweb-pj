import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CurrentUserService } from "../../current-user.service";

@Component({
  selector: 'app-confirm-register',
  templateUrl: './confirm-register.component.html',
  styleUrls: ['./confirm-register.component.css']
})
export class ConfirmRegisterComponent implements OnInit {

  submitting: boolean = true;
  error: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private currentUser: CurrentUserService) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const token = params.token;
      console.log(params, token);
      if (!token) {
        this.error = true;
      } else {
        this.currentUser.confirmRegister(token)
          .subscribe(() => this.router.navigate(['/app/login'],
            { queryParams: { confirmRegistration: true } }),
            () => this.error = true)
      }
      this.submitting = false;
    })
  }

}
