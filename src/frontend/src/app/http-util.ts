import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { catchError } from "rxjs/internal/operators";

export function convert(data): HttpParams {
  let params = new HttpParams();
  Object.keys(data).map(k => {
    params = params.append(k, data[k])
  });
  return params;
}

export function sendFormData<T>(url, data, http: HttpClient): Observable<T> {
  const body = Object.keys(data).reduce((acc, cur) => {
    acc.set(cur, data[cur]);
    return acc;
  }, new URLSearchParams());
  return http.post<T>(url, body.toString(), {
    headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
  })
}

export function getOrError<T>(fun: () => Observable<T>,
                              success: (T) => void,
                              error: (any) => void) {
  return fun().pipe(catchError(err => {
    error(err);
    return new Observable()
  }))
    .subscribe(success)
}
