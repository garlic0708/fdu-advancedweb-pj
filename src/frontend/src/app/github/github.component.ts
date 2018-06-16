import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Http, Jsonp, Headers} from '@angular/http';


@Component({
  selector: 'app-github',
  templateUrl: './github.component.html',
  styleUrls: ['./github.component.css']
})
export class GithubComponent implements OnInit {

  private headers = new Headers({'Content-Type': 'application/json'});
  code:string;
  access_token:string;

  constructor(private http: Http, private activatedRoute: ActivatedRoute) {
     activatedRoute.queryParams.subscribe(queryParams => {
          this.code = queryParams.code;
      });

    //client_id=18d8a971a997b63d74b7&client_secret=01dbd653e6f37ce400c971282f179f1b463c4463&code=a07bbbeda08385591e07&callback=JSONP_CALLBACK";
    //https://github.com/login/oauth/access_token

    let that = this;

    let url="https://github.com/login/oauth/access_token";
    this.http.post(url, {client_id: "18d8a971a997b63d74b7", client_secret:"01dbd653e6f37ce400c971282f179f1b463c4463", code: this.code}, {headers: this.headers}).subscribe(function (data) {
      that.access_token = data['_body'];

      if (that.access_token.charAt(0) == 'e') { //code error
        }

      that.access_token = that.access_token.slice(13, 53);
      console.log("access_token_________", that.access_token);

      url="https://api.github.com/user?access_token=" + that.access_token;
      console.log(url);
      that.http.get(url).subscribe(function (data) {
        console.log("data___________", data);
      },function (err) {
        console.log(2,"---",err);
      })

   },function (err) {
      console.log("access_token","---",err);
    })

  }

  ngOnInit() {

  }

  test() {
    console.log(this.code);
  }

}
