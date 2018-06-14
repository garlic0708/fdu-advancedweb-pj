import { Component, ViewChild } from '@angular/core';
import { CurrentUserService } from "./current-user.service";
import { catchError } from "rxjs/internal/operators";
import { Observable } from "rxjs/index";
import { getOrError } from "./http-util";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private http: HttpClient) {
  }

  title = 'app';

  log(e): void {
    console.log(e)
  }

  test() {
    // this.http.post('http://localhost:8080/api/mindmap/manipulate/43', [
    //     {action: 'addNode', parentId: 56, id: 100, value: 'new test'},
    //     {action: 'changeName', id: 50, value: 'change test'},
    //     {action: 'changeColor', id: 25, value: 'green'},
    //     {action: 'removeNode', id: 52},
    //   ]
    // ).subscribe(data => console.log(data));
  }
}

