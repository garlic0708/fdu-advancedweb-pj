///<reference path="../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {GetQuestionService} from '../answer-question/get-question.service';
import {MultipleChoiceQuestion, ShortAnswerQuestion} from "../answer-question/question";
import {Observable} from "rxjs/internal/Observable";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-release-question',
  templateUrl: './release-question.component.html',
  styleUrls: ['./release-question.component.css']
})
export class ReleaseQuestionComponent implements OnInit {
  question$: Observable<MultipleChoiceQuestion> | Observable<ShortAnswerQuestion>;
  qid: number = 1;
  questionRouterType = 'shortAnswer';
  // questionRouterType = 'multipleChoice';
  question_type = new FormControl(this.questionRouterType);
  isModified: boolean;
  questionForm: FormGroup;
  choice_question: string;
  choice_answer;
  answer_question: string;
  answer_answer: string;

  // choices = new FormArray([]);

  constructor(private fb: FormBuilder, private getQuestionService: GetQuestionService) {
    this.isModified = false;
    this.choice_question = "";
    this.choice_answer = ["", ""];
    this.answer_question = "";
    this.answer_answer = "";
    this.questionForm = new FormGroup({
      content: new FormControl(),
      choices: new FormArray(this.qid ? [] : [
        this.fb.group(new Choice()),
        this.fb.group(new Choice()),
      ]),
      correct: new FormControl()
    }, [
      (group: FormGroup) => {
        const correct = group.controls['correct'];
        if ((group.controls['choices'] as FormArray).length <=
          correct.value)
          correct.setErrors({'correctNotAmongChoices': true});
        return null
      }
    ]);
    if (!this.qid)
      this.questionForm.addControl(
        'questionType', new FormControl(this.qid ? this.questionRouterType : 'multipleChoice'))
  }

  ngOnInit() {
    this.question$ = this.getQuestionService.getQuestion(this.questionRouterType, this.qid)
      .pipe(map(question => {
        this.content.setValue(question.content);
        if (this.questionRouterType == 'multipleChoice') {
          let answerKeys = this.getAnswerKeys(question);
          this.questionForm.setControl('choices',
            new FormArray(answerKeys.map(k =>
              this.fb.group({content: question.answers[k]} as Choice))));
          this.correct.setValue(answerKeys.findIndex(x => x == question.correct));
        } else
          this.correct.setValue(question.correct);
        return question;
      }));
    console.log(this.question$)
  }

  onSubmit() {
  }

  get content(): FormControl {
    return this.questionForm.get('content') as FormControl
  }

  get choices(): FormArray {
    return this.questionForm.get('choices') as FormArray;
  }

  get correct(): FormControl {
    return this.questionForm.get('correct') as FormControl;
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

  // valid() {
  //   if (this.questionType.value == null) return true;
  //   if (this.questionType.value == "choice") {
  //     if (this.choice_question == "") return true;
  //     for (let i in this.choice_answer)
  //       if (this.choice_answer[i] == "") return true;
  //   }
  //   else {
  //     if (this.answer_question == "" || this.answer_answer == "") return true;
  //   }
  //   return false;
  // }

  getAnswerKeys(question): string[] {
    return Object.keys(question.answers);
  }

  test() {
    console.log(this.choice_question);
    console.log(this.choice_answer)
  }

  modify() {
    this.isModified = true;
  }
}

export class Choice {
  content = '';
}
