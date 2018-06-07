import { Component, OnInit } from '@angular/core';
import {MindmapEvent} from "../mindmap-body/mindmap-event";
import {Attachment} from "./attachment";
import {mockData} from "../../assets/mock-data";

@Component({
  selector: 'app-mindmap-holder',
  templateUrl: './mindmap-holder.component.html',
  styleUrls: ['./mindmap-holder.component.css']
})
export class MindmapHolderComponent implements OnInit {

  attachments: Attachment[] = mockData['GET /api/node/attachments/:id'];

  availableColors = {
    'multipleChoice': 'warn',
    'shortAnswer': 'accent',
    'courseWare': 'primary',
    'resource': '',
  };

  constructor() { }

  ngOnInit() {
  }

  mindmapEvent(e: MindmapEvent) {
    switch (e.eventName) {
      case 'nodeSelectionChanged':

    }
  }

}
