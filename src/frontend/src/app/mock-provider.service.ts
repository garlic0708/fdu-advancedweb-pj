import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { of } from 'rxjs';
import { delay, mapTo } from "rxjs/internal/operators";
import { mockData } from "../assets/mock-data";
import escapeRegExp = require('lodash/escapeRegExp');

@Injectable()
export class MockProviderService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // console.log(req);
    const key = `${req.method} ${req.url}`;
    const mockDatum = mockData[
      Object.keys(mockData).find(k => {
        k = escapeRegExp(k);
        k = k.replace(/:[A-Za-z]*(\\\/|$)/, "\\d+$1");
        // console.log(k, new RegExp(k), key);
        return new RegExp(k).test(key);
      })];
    const response = {
      status: 200,
    };
    if (mockDatum && mockDatum !== true)
      response['body'] = mockDatum;
    console.log(mockDatum);
    return mockDatum ?
      of(null).pipe(mapTo(new HttpResponse(response)), delay(2000))
      : next.handle(req);
  }

  constructor() {
  }
}
