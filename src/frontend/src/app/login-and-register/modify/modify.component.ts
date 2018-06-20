import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { CurrentUserService } from "../../current-user.service";
import { Router } from "@angular/router";

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.css']
})
export class ModifyComponent implements OnInit {

  formGroup: FormGroup;

  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-zA-Z0-9]{6,32}$")
  ]);
  confirmPasswordFormControl = new FormControl('', [
    Validators.required,
  ]);

  matcher = new MyErrorStateMatcher();
  submitting: boolean = false;

  constructor(private fb: FormBuilder, private currentUser: CurrentUserService,
              private router: Router) {
    this.formGroup = this.fb.group({
      oldPassword: new FormControl(),
      password: this.passwordFormControl,
      confirmPassword: this.confirmPasswordFormControl,
    }, {
      validator: [
        (group: FormGroup) => {
          const confirm = group.controls['confirmPassword'];
          if (group.controls['password'].value != confirm.value)
            confirm.setErrors({ pattern: true });
          return null
        }
      ]
    })
  }

  ngOnInit() {
  }

  submit() {
    this.submitting = true;
    this.currentUser.changePassword(this.formGroup.value)
      .subscribe(() => {
        this.submitting = false;
        this.router.navigateByUrl('/app/login');
      })
  }
}
