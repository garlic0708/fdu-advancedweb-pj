import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {mockData} from "../assets/mock-data";
import * as _ from 'lodash';

@Injectable()
export class MockProviderService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(req.url);
    const key = `${req.method} ${req.url}`;
    const mockDatum = mockData[
      Object.keys(mockData).find(k => {
        k = _.escapeRegExp(k);
        k = k.replace(/:[A-Za-z]*(\\\/|$)/, "\\d+$1");
        console.log(k, new RegExp(k), key);
        return new RegExp(k).test(key);
      })];
    const response = {
      status: 200,
    };
    if (mockDatum && mockDatum !== true)
      response['body'] = mockDatum;
    return mockDatum ? Observable.create(new HttpResponse(response)) : next.handle(req);
  }

  constructor() { }
}
