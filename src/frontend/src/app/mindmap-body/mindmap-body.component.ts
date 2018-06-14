import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import {init, testMap} from '../../assets/mapjs/mapjs-util'
import {MindmapEvent} from "./mindmap-event";
import {defaultColor, level1Color} from '../../assets/mapjs/theme';
import { Observable } from "rxjs/index";

@Component({
  selector: 'app-mindmap-body',
  templateUrl: './mindmap-body.component.html',
  styleUrls: ['./mindmap-body.component.css']
})

export class MindmapBodyComponent implements OnInit, AfterViewInit {

  @ViewChild('container') container;
  mapModel;
  selectedNodeId: number;
  rootIds: number[];

  @Output() manipulation: EventEmitter<MindmapEvent> = new EventEmitter<MindmapEvent>();

  @Input() map;
  private _map;

  constructor() {
  }

  ngOnInit() {
    // this._map = testMap;
    this._map = this.map;
    console.log(this._map)
  }

  ngAfterViewInit(): void {
    this.rootIds = Object.keys(this._map.ideas).map(k => this._map.ideas[k].id);
    this.mapModel = init(this.container.nativeElement, this._map);
    this.addManipulationListeners();
    this.addSelectionListener();
    console.log(this.mapModel.getIdea())
  }

  private addSelectionListener() {
    this.mapModel.addEventListener('nodeSelectionChanged', e => {
      if (this.selectedNodeId != e) {
        this.selectedNodeId = e;
        this.emitSelectionChanged();
      }
    });
    this.selectedNodeId = this.mapModel.getSelectedNodeId();
    this.emitSelectionChanged();
  }

  private addManipulationListeners() {
    const events = ['nodeCreated', 'nodeRemoved', 'nodeTitleChanged', 'colorChanged', 'parentNodeChanged'];
    events.forEach(eventName => {
      this.mapModel.addEventListener(eventName, e => {
        this.manipulation.emit({
          eventName,
          event: e,
          id: e.id,
          parentId: this.mapModel.getIdea().findParent(e.id).id
        })
      })
    });
  }

  private emitSelectionChanged() {
    this.manipulation.emit({
      eventName: 'nodeSelectionChanged',
      id: this.selectedNodeId,
    })
  }

  addSubIdea() {
    this.mapModel.addSubIdea();
    console.log(this.mapModel.getIdea())
  }

  removeSubIdea() {
    this.mapModel.removeSubIdea()
  }

  setIdeaColor(color) {
    this.mapModel.updateStyle('angular', 'background', color)
  }

  get selectedColor() {
    return this.mapModel.getSelectedStyle('background') ||
      (this.rootIds.includes(this.selectedNodeId) ? level1Color : defaultColor)
  }

  set editingEnabled(value: boolean) {
    this.mapModel.setEditingEnabled(value)
  }

  undo() {
    this.mapModel.undo()
  }

  redo() {
    this.mapModel.redo()
  }

  get canUndo() {
    return this.mapModel && this.mapModel.getIdea().canUndo()
  }

  get canRedo() {
    return this.mapModel && this.mapModel.getIdea().canRedo()
  }
}
