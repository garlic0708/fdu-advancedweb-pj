import { Component, OnInit, ViewChild } from '@angular/core';
import { MindmapEvent } from "../mindmap-body/mindmap-event";
import { Attachments } from "./attachment";
import { NodeService } from "./node.service";
import { Observable } from "rxjs/index";
import {
  trigger,
  animate,
  transition
} from '@angular/animations';
import { Manipulation } from "./manipulation";
import remove = require('lodash/remove');
import findLast = require('lodash/findLast');
import get = require('lodash/get');
import { MatSnackBar } from "@angular/material";
import * as tinyColor from 'tinycolor2';

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
  map$: Observable<any>;
  _saving: boolean = false;

  private _selectedColor;
  @ViewChild('body') body;
  private manipulations: Manipulation[] = [];
  private mapId: number = 43;

  constructor(private nodeService: NodeService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.map$ = this.nodeService.getMap(this.mapId); // todo
  }

  get selectedColor() {
    return this._selectedColor
  }

  set selectedColor(c) {
    this.body.setIdeaColor(c);
    this._selectedColor = c
  }

  saveManipulation() {
    this.saving = true;
    this.nodeService.manipulate(this.mapId, this.manipulations)
      .subscribe(() => {
        this.saving = false;
        this.manipulations = [];
        this.snackBar.open('Save complete')
      })
  }

  get colorForeground() {
    return tinyColor(this.selectedColor).isDark() ? 'white' : 'black'
  }

  get saving() {
    return this._saving
  }

  set saving(value) {
    this._saving = value;
    this.body.editingEnabled = value;
  }

  mindmapEvent(e: MindmapEvent) {
    console.log(e);

    const insertDistinct = (value, action) => {
      const prev = findLast(this.manipulations, {id: e.id, action});
      if (prev) prev.value = value;
      else this.manipulations.push({
        action,
        id: e.id,
        value,
      });
    };

    switch (e.eventName) {
      case 'nodeSelectionChanged':
        // This is to circumvent the "Expression has changed after it was checked" error
        setTimeout(() => {
          this._selectedColor = this.body.selectedColor;
          this.attachments$ = this.nodeService.getAttachments(e.id);
        });
        break;
      case 'nodeCreated':
        const removal = remove(this.manipulations, {action: 'removeNode', id: e.id});
        if (!removal.length) this.manipulations.push({
          action: 'addNode',
          id: e.id,
          parentId: e.parentId,
        });
        break;
      case 'nodeRemoved':
        const creation = findLast(this.manipulations, {action: 'addNode', id: e.id});
        remove(this.manipulations, {id: e.id});
        if (!creation) this.manipulations.push({
          action: 'removeNode',
          id: e.id,
        });
        break;
      case 'nodeTitleChanged':
        insertDistinct(e.event.title, 'changeName');
        break;
      case 'colorChanged':
        insertDistinct(get(e.event, 'attr.style.background'), 'changeColor');
        setTimeout(() => this._selectedColor = this.body.selectedColor);
        break;
      case 'parentNodeChanged':
        const prev = findLast(this.manipulations, ({action, id}) =>
          id == e.id && (action == 'addNode' || action == 'changeParent')
        );
        if (prev) prev.parentId = e.parentId;
        else this.manipulations.push({
          action: 'changeParent',
          id: e.id,
          parentId: e.parentId,
        })
    }
    console.log(this.manipulations)
  }

}
