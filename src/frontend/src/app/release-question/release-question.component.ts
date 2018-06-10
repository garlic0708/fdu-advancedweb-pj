///<reference path="../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: 'app-release-question',
  templateUrl: './release-question.component.html',
  styleUrls: ['./release-question.component.css']
})
export class ReleaseQuestionComponent implements OnInit {
  questionForm: FormGroup;

  // choices = new FormArray([]);

  constructor(private fb: FormBuilder) {
    this.questionForm = new FormGroup({
      questionType: new FormControl(),
      choices: new FormArray([
        this.fb.group(new Choice()),
        this.fb.group(new Choice()),
      ]),
      correct: new FormControl(0),
    }, [
      (group: FormGroup) => {
        const correct = group.controls['correct'];
        if ((group.controls['choices'] as FormArray).length <=
          correct.value)
          correct.setErrors({'correctNotAmongChoices': true});
        return null
      }
    ]);
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

  get questionType() {
    return this.questionForm.get('questionType') as FormControl
  }

  deleteChoice(index) {
    if (index === this.choices.length - 1 && index === this.correct.value) {
      this.correct.setValue(index - 1);
    }
    this.choices.removeAt(index);
  }

  test() {
    console.log(this.questionForm.value, this.questionType)
  }
}

export class Choice {
  content = '';
}
