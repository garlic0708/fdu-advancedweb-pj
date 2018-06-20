import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CourseWare, Question, Resource } from "../attachment";

@Component({
  selector: 'app-attachment-display',
  templateUrl: './attachment-display.component.html',
  styleUrls: ['./attachment-display.component.css']
})
export class AttachmentDisplayComponent implements OnInit {

  @Input() attachmentList: Question | CourseWare | Resource;
  @Input() displayNewItem: boolean | 'needSave';
  @Input() type: string;
  @Output() chooseItem: EventEmitter<any> = new EventEmitter<any>();
  @Output() newItem: EventEmitter<any> = new EventEmitter<any>();

  constructor() {
  }

  ngOnInit() {
  }

  itemClicked(item) {
    this.chooseItem.emit(item)
  }

  newItemClicked() {
    if (this.displayNewItem == true)
      this.newItem.emit()
  }

}
