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
  choice_question: string;
  choice_answer;
  answer_question: string;
  answer_answer: string;

  // choices = new FormArray([]);

  constructor(private fb: FormBuilder) {
    this.choice_question = "";
    this.choice_answer = ["", ""];
    this.answer_question = "";
    this.answer_answer = "";
    this.questionForm = new FormGroup({
      questionType: new FormControl(),
      choices: new FormArray([
        this.fb.group(new Choice()),
        this.fb.group(new Choice()),
      ]),
      correct: new FormControl(0)
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

  get correct() {
    return this.questionForm.get('correct');
  }

  addChoices() {
    this.choice_answer.push("");
    this.choices.push(this.fb.group(new Choice()));
  }

  get questionType() {
    return this.questionForm.get('questionType');
  }

  deleteChoice(index) {
    this.choice_answer.splice(index, 1)
    if (index === this.choices.length - 1 && index === this.correct.value) {
      this.correct.setValue(index - 1);
    }
    this.choices.removeAt(index);
  }

  valid() {
    if (this.questionType.value == null) return true;
    if (this.questionType.value == "choice") {
      if (this.choice_question == "") return true;
      for (let i in this.choice_answer)
        if (this.choice_answer[i] == "") return true;
    }
    else {
      if (this.answer_question == "" || this.answer_answer == "") return true;
    }
    return false;
  }

  test() {
    console.log(this.choice_question);
    console.log(this.choice_answer)
  }
}

export class Choice {
  content = '';
}
