import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { init, testMap } from '../../assets/mapjs/mapjs-util'
import { MindmapEvent } from "./mindmap-event";
import { defaultColor, level1Color, foreGround, lightForeGround } from '../../assets/mapjs/theme';
import * as tinyColor from 'tinycolor2';

@Component({
  selector: 'app-mindmap-body',
  templateUrl: './mindmap-body.component.html',
  styleUrls: ['./mindmap-body.component.css']
})

export class MindmapBodyComponent implements OnInit, AfterViewInit {

  @ViewChild('container') container;
  private mapModel;
  private selectedNodeId: number;
  private rootIds: number[];

  @Output() manipulation: EventEmitter<MindmapEvent> = new EventEmitter<MindmapEvent>();

  @Input() map;
  private _map;

  private modelInitResolve: Function;
  private modelInitPromise = new Promise(resolve => this.modelInitResolve = resolve);

  constructor() {
  }

  ngOnInit() {
    // this._map = testMap;
    this._map = this.map;
    console.log(this._map)
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.rootIds = Object.keys(this._map.ideas).map(k => this._map.ideas[k].id);
      this.mapModel = init(this.container.nativeElement, this._map);
      this.modelInitResolve();
      this.addManipulationListeners();
      this.addSelectionListener();
      console.log(this.mapModel.getIdea())
    })
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
    const events = ['nodeCreated', 'nodeRemoved', 'nodeTitleChanged',
      'colorChanged', 'foreGroundChanged', 'parentNodeChanged'];
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

  addSiblingIdeaBefore() {
    this.mapModel.addSiblingIdeaBefore();
  }

  removeSubIdea() {
    this.mapModel.removeSubIdea()
  }

  setIdeaColor(color, foreGround?) {
    this.mapModel.updateStyle('angular', foreGround ? 'color' : 'background', color)
  }

  get selectedColor() {
    return this.mapModel.getSelectedStyle('background') ||
      (this.rootIds.includes(this.selectedNodeId) ? level1Color : defaultColor)
  }

  get selectedForeGround() {
    return this.mapModel.getSelectedStyle('color') ||
      (tinyColor(this.selectedColor).isDark() ? lightForeGround : foreGround)
  }

  @Input() set editingEnabled(value: boolean) {
    this.modelInitPromise.then(() =>
      this.mapModel.setEditingEnabled(value))
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

  get isRootSelected() {
    return this.rootIds && this.rootIds.includes(this.selectedNodeId)
  }
}
