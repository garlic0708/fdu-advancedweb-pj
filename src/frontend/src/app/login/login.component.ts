import {Component, OnInit} from '@angular/core';
import {Http, Jsonp, Headers} from '@angular/http';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  user: string;

  constructor(private http: Http, private jsonp: Jsonp) {
    this.email = "";
    this.password = "";
    this.user = "";
  }

  ngOnInit() {
  }


  onSubmit() {
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }




  valid() {
    if (this.email == "" || this.password == "" || this.user == "") return true;
    else return false;
  }

  test233() {
    console.log(this.user);
  }

}
