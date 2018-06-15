import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-upload-resource',
  templateUrl: './upload-resource.component.html',
  styleUrls: ['./upload-resource.component.css']
})
export class UploadResourceComponent implements OnInit {

  type: string;
  url: string;
  description: string;

  constructor() {
      this.url = "";
      this.description = "";
  }

  ngOnInit() {
  }

  onSubmit() {
  }

  test() {
    console.log(this.type);
  }

  valid() {
    if (this.url == "" || this.description == "") return true;
    return false;
  }

}
