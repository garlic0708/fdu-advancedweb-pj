import { Component, OnInit} from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

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
  email: string;
  name: string;
  password: string;
  confirmPassword: string;

  confirmPasswordPattern = "^$";

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
    Validators.pattern(this.confirmPasswordPattern)
  ]);

  matcher = new MyErrorStateMatcher();

  constructor() { }

  ngOnInit() {
  }

  updateConfirmPasswordControl() {
    var save = this.confirmPassword;
    this.confirmPasswordPattern = "^" + this.password + "$";
    this.confirmPasswordFormControl = new FormControl('', [
      Validators.required,
      Validators.pattern(this.confirmPasswordPattern)
    ]);
  }

  test233() {
    alert(this.confirmPasswordPattern);
  }

}
