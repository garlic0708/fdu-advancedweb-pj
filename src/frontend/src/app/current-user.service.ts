import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { RegisterForm, User } from './user';
import * as _ from 'lodash';
import { Observable } from "rxjs/index";
import { map } from 'rxjs/operators'
import { convert } from "./http-util";

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  currentUser: User = null;
  private loginUrl = 'login';
  private registerUrl = 'register';
  private logoutUrl = 'logout';

  constructor(private http: HttpClient) {
  }

  login(email, password): Observable<User> {
    return this.http.post<User>(this.loginUrl, convert({
      email, password,
    })).pipe(map(user => this.currentUser = user));
  }

  register(form): Observable<User> {
    return this.http.post<User>(this.registerUrl, form)
      .pipe(map(user => this.currentUser = user));
  }

  logout(): Observable<Response> {
    return this.http.post(this.logoutUrl, null)
      .pipe(map(() => this.currentUser = null))
  }

  get currentRole() {
    return _.has(this.currentUser, 'role')
  }
}
