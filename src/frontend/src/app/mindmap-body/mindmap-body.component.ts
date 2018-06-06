import {AfterViewInit, Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {init} from '../../assets/mapjs/mapjs-util'

@Component({
  selector: 'app-mindmap-body',
  templateUrl: './mindmap-body.component.html',
  styleUrls: ['./mindmap-body.component.css']
})
export class MindmapBodyComponent implements OnInit, AfterViewInit {

  @ViewChild('container') container;
  mapModel;

  @Output() manipulation: EventEmitter<any> = new EventEmitter<any>();

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.mapModel = init(this.container.nativeElement);
    const events = ['nodeCreated', 'nodeRemoved', 'nodeAttrChanged'];
    events.forEach(eventName => {
      this.mapModel.addEventListener(eventName, e => {
        this.manipulation.emit({
          eventName,
          id: e.id,
          rootId: e.rootId,
          title: e.title,
        })
      })
    })
  }

  addSubIdea() {
    this.mapModel.addSubIdea()
  }

  removeSubIdea() {
    this.mapModel.removeSubIdea()
  }
}
