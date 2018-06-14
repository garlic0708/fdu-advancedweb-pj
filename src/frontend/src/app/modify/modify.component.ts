import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

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
  old_password: string;
  password: string;
  confirmPassword: string;

  confirmPasswordPattern = "^$";

  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-zA-Z0-9]{6,32}$")
  ]);
  confirmPasswordFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(this.confirmPasswordPattern)
  ]);

  matcher = new MyErrorStateMatcher();

  constructor() {
    this.old_password = "";
    this.password = "";
    this.confirmPassword = "";
  }

  ngOnInit() {
  }

  updateConfirmPasswordControl() {
    this.confirmPasswordPattern = "^" + this.password + "$";
    this.confirmPasswordFormControl = new FormControl('', [
      Validators.required,
      Validators.pattern(this.confirmPasswordPattern)
    ]);
  }

  valid() {
    if (this.old_password == "") return true;
    if (this.password.search("^[a-zA-Z0-9]{6,32}$") == -1) return true;
    if (this.password != this.confirmPassword) return true;
    return false;
  }

  test233() {
    console.log(this.old_password);
    console.log(this.password);
    console.log(this.confirmPassword);
  }
}
