import { Component, OnInit } from '@angular/core';
import { FileItem, FileUploader } from 'ng2-file-upload';
import { NodeService } from "../node.service";
import { ActivatedRoute } from "@angular/router";
import fileSize = require('file-size');
import { BehaviorSubject, Subject } from "rxjs/index";

@Component({
  selector: 'app-upload-course',
  templateUrl: './upload-course.component.html',
  styleUrls: ['./upload-course.component.css'],
})
export class UploadCourseComponent implements OnInit {

  constructor(private nodeService: NodeService,
              private route: ActivatedRoute) {
  }

  uploader: FileUploader;
  queue: FileItem[] = [];

  columns = ['name', 'size', 'progress', 'status', 'actions'];

  hasBaseDropZoneOver: boolean = false;

  fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }

  getFileSize(size) {
    return fileSize(size).human('jedec')
  }

  ngOnInit() {
    this.route.url.subscribe(url => {
      console.log('url is', url);
      const type = url[0].path;
      const id = +url[1].path;
      this.uploader = this.nodeService.getUploader(type);
      this.uploader.onAfterAddingFile = () => this.queue = this.uploader.queue;
      this.uploader.onSuccessItem = (_, response) =>
        this.nodeService.uploadSuccess(type, response, id);
      this.uploader.onBuildItemForm = this.nodeService.uploadItemForm(this.uploader, id)
    });
  }

}
