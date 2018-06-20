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
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  formGroup: FormGroup;

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"),
  ]);
  nameFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-zA-Z0-9\u4e00-\u9fa5]{6,32}$"),
  ]);
  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-zA-Z0-9]{6,32}$")
  ]);
  confirmPasswordFormControl = new FormControl('', [
    Validators.required,
  ]);
  roleFormControl = new FormControl('TEACHER', [Validators.required,]);

  matcher = new MyErrorStateMatcher();
  submitting: boolean = false;

  constructor(private fb: FormBuilder, private currentUser: CurrentUserService,
              private router: Router) {
    this.formGroup = this.fb.group({
      email: this.emailFormControl,
      name: this.nameFormControl,
      password: this.passwordFormControl,
      passwordRepeated: this.confirmPasswordFormControl,
      role: this.roleFormControl,
    }, {
      validator: [
        (group: FormGroup) => {
          console.log(group);
          const confirm = group.controls['passwordRepeated'];
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
    this.currentUser.register(this.formGroup.value)
      .subscribe(() => {
        this.submitting = false;
        this.router.navigateByUrl('/')
      })
  }
}
