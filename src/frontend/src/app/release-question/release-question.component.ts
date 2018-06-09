///<reference path="../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-release-question',
  templateUrl: './release-question.component.html',
  styleUrls: ['./release-question.component.css']
})
export class ReleaseQuestionComponent implements OnInit {
  questionForm: FormGroup;
  questionType = new FormControl();

  // choices = new FormArray([]);

  constructor(private fb: FormBuilder) {
    this.questionForm = new FormGroup({
      questionType: new FormControl(),
      choices: new FormArray([]),
      correct: new FormControl(),
    });
  }


  ngOnInit() {
  }


  onSubmit() {
  }

  get choices(): FormArray {
    return this.questionForm.get('choices') as FormArray;
  }

  get correct(): FormControl {
    return this.questionForm.get('correct') as FormControl;
  }

  addChoices() {
    this.choices.push(this.fb.group(new Choice()));
  }

  deleteChoice(index) {
    this.choices.removeAt(index);
  }

  test() {
    console.log(this.questionForm.value)
  }
}

export class Choice {
  content = '';
}
