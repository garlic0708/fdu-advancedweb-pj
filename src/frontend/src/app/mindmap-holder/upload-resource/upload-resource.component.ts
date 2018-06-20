import { Component, OnInit } from '@angular/core';
import { NodeService } from "../node.service";
import { ActivatedRoute } from "@angular/router";
import { CurrentUserService } from "../../current-user.service";

@Component({
  selector: 'app-upload-resource',
  templateUrl: './upload-resource.component.html',
  styleUrls: ['./upload-resource.component.css']
})
export class UploadResourceComponent implements OnInit {

  type: string;
  url: string;
  description: string;

  submitting: boolean = false;
  editing: boolean = false;
  nodeId: number;
  rid: number;

  constructor(private nodeService: NodeService,
              private route: ActivatedRoute,
              private currentUser: CurrentUserService) {
    this.url = "";
    this.description = "";
  }

  ngOnInit() {
    this.route.paramMap.subscribe(param => {
      this.rid = +param.get('rid');
      this.nodeId = +param.get('nodeId');
      if (this.rid) {
        this.type = 'url';
        this.nodeService.getResource(this.rid)
          .subscribe(resource => {
            this.description = resource.name;
            this.url = resource.url;
          })
      } else
        this.editing = false
    })
  }

  onSubmit() {
    this.submitting = true;
    if (!this.rid)
      this.nodeService.addResource(this.nodeId, this.description, this.url)
        .subscribe(() => {
          this.submitting = false;
          this.url = "";
          this.description = ""
        });
    else
      this.nodeService.updateResource(this.rid, this.description, this.url)
        .subscribe(() => {
          this.submitting = false;
          this.editing = false;
        })
  }

  invalid() {
    return this.url == "" || this.description == "";
  }

  canEdit() {
    return this.currentUser.currentUserRole == 'TEACHER'
  }
}
