import { Component, OnInit } from '@angular/core';
import { MindmapEvent } from "../mindmap-body/mindmap-event";
import { Attachments } from "./attachment";
import { NodeService } from "./node.service";
import { Observable } from "rxjs/index";
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

@Component({
  selector: 'app-mindmap-holder',
  templateUrl: './mindmap-holder.component.html',
  styleUrls: ['./mindmap-holder.component.css'],
  animations: [
    trigger('disappear', [
      transition('* => void', animate('100ms ease-in-out'))
    ])
  ]
})
export class MindmapHolderComponent implements OnInit {

  attachments$: Observable<Attachments>;

  constructor(private nodeService: NodeService) {
  }

  ngOnInit() {
    // this.nodeService.test()
  }

  mindmapEvent(e: MindmapEvent) {
    switch (e.eventName) {
      case 'nodeSelectionChanged':
        // This is to circumvent the "Expression has changed after it was checked" error
        setTimeout(() => this.attachments$ = this.nodeService.getAttachments(e.id));
    }
  }

}
