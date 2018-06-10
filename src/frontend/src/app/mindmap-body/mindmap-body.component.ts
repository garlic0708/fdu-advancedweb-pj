import {AfterViewInit, Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {init} from '../../assets/mapjs/mapjs-util'
import {MindmapEvent} from "./mindmap-event";

@Component({
  selector: 'app-mindmap-body',
  templateUrl: './mindmap-body.component.html',
  styleUrls: ['./mindmap-body.component.css']
})

export class MindmapBodyComponent implements OnInit, AfterViewInit {

  @ViewChild('container') container;
  mapModel;
  selectedNodeId: number;

  @Output() manipulation: EventEmitter<MindmapEvent> = new EventEmitter<MindmapEvent>();

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.mapModel = init(this.container.nativeElement);
    const events = ['nodeCreated', 'nodeRemoved', 'nodeAttrChanged',];
    events.forEach(eventName => {
      this.mapModel.addEventListener(eventName, e => {
        this.manipulation.emit({
          eventName,
          event: e,
          id: e.id,
        })
      })
    });
    this.mapModel.addEventListener('nodeSelectionChanged', e => {
      if (this.selectedNodeId != e) {
        this.selectedNodeId = e;
        this.emitSelectionChanged();
      }
    });
    this.selectedNodeId = this.mapModel.getSelectedNodeId();
    this.emitSelectionChanged();
  }

  private emitSelectionChanged() {
    this.manipulation.emit({
      eventName: 'nodeSelectionChanged',
      id: this.selectedNodeId,
    })
  }

  addSubIdea() {
    this.mapModel.addSubIdea()
  }

  removeSubIdea() {
    this.mapModel.removeSubIdea()
  }
}
