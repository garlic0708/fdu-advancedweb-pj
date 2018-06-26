import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { RegisterForm, User } from './user';
import get = require('lodash/get');
import { BehaviorSubject, Observable, of, Subject } from "rxjs/index";
import { map } from 'rxjs/operators'
import { convert, sendFormData } from "./http-util";
import { PartialObserver } from "rxjs/src/internal/types";
import { RequestOptions } from "@angular/http";

const STORAGE_KEY = 'currentUser';

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  private _currentUser: BehaviorSubject<User> = new BehaviorSubject(null);
  private loginUrl = '/login';
  private registerUrl = '/register';
  private logoutUrl = '/logout';
  private changePasswordUrl = "/changePassword";
  private confirmRegisterUrl = "/registrationConfirm";

  private storage = window.sessionStorage;

  constructor(private http: HttpClient) {
    const userJson = this.storage.getItem(STORAGE_KEY);
    if (userJson)
      this._currentUser.next(JSON.parse(userJson))
  }

  login(email, password): Observable<User> {
    return sendFormData<User>(this.loginUrl, { email, password }, this.http)
      .pipe(map(user => {
        this._currentUser.next(user);
        this.storage.setItem(STORAGE_KEY, JSON.stringify(user));
        return user
      }));
  }

  register(form): Observable<any> {
    return sendFormData(this.registerUrl, form, this.http)
  }

  confirmRegister(token): Observable<any> {
    return this.http.get<User>(this.confirmRegisterUrl,
      { params: new HttpParams().set('token', token) })
      // .pipe(map(user => {
      //   this._currentUser.next(user);
      //   this.storage.setItem(STORAGE_KEY, JSON.stringify(user));
      //   return user
      // }));
  }

  changePassword(form): Observable<any> {
    return sendFormData(this.changePasswordUrl, form, this.http)
      .pipe(map(response => {
        this._currentUser.next(null);
        this.storage.removeItem(STORAGE_KEY);
        return response
      }))
  }

  logout(): Observable<Response> {
    return this.http.post(this.logoutUrl, null)
      .pipe(map((response: Response) => {
        this._currentUser.next(null);
        this.storage.removeItem(STORAGE_KEY);
        return response
      }))
    // return of(null)
  }

  get currentUser(): Observable<User> {
    return this._currentUser
  }

  get currentUserRole(): 'STUDENT' | 'TEACHER' {
    return this._currentUser.value.role
  }

  subscribe(x) {
    this._currentUser.subscribe(x)
  }
}
