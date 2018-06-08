import { Component, OnInit } from '@angular/core';
import { Http  } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  private heroesUrl = 'api/node/attachments/:id';

  constructor(private http: Http) { }

  ngOnInit() {
  }

  getCourse() {
    console.log(this.http.get(this.heroesUrl)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError));
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }


}
