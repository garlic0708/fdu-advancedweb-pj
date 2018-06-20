import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MultipleChoiceQuestion, ShortAnswerQuestion } from "../answer-question/question";
import { Observable } from "rxjs/internal/Observable";
import { map } from "rxjs/operators";
import { ActivatedRoute, Router } from "@angular/router";
import { NodeService } from "../node.service";

@Component({
  selector: 'app-release-question',
  templateUrl: './release-question.component.html',
  styleUrls: ['./release-question.component.css']
})
export class ReleaseQuestionComponent implements OnInit {
  question$: Observable<MultipleChoiceQuestion> | Observable<ShortAnswerQuestion>;
  qid: number; // non-null for editing, null for modifying
  questionRouterType = 'shortAnswer';
  // questionRouterType = 'multipleChoice';
  isModified: boolean;
  questionForm: FormGroup;
  submitting: boolean = false;

  // choices = new FormArray([]);

  constructor(private fb: FormBuilder,
              private nodeService: NodeService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  private initFormControls() {
    this.questionForm = new FormGroup({
      content: new FormControl(),
      choices: new FormArray(this.qid ? [] : [
        this.fb.group(new Choice()),
        this.fb.group(new Choice()),
      ]),
      correct: new FormControl(0)
    }, [
      (group: FormGroup) => {
        const correct = group.controls['correct'];
        if ((group.controls['choices'] as FormArray).length <=
          correct.value)
          correct.setErrors({ 'correctNotAmongChoices': true });
        return null
      }
    ]);
    if (!this.qid)
      this.questionForm.addControl(
        'questionType',
        new FormControl('multipleChoice'))
  }

  ngOnInit() {
    this.route.paramMap.subscribe(param => {
      this.isModified = false;
      this.questionRouterType = param.get('qType');
      this.qid = +param.get('qid');
      this.initFormControls();
      if (this.qid) this.question$ = this.getQuestion();
    });
  }

  private getQuestion() {
    return this.nodeService.getQuestion(this.questionRouterType, this.qid)
      .pipe(map(question => {
        this.initFormControlValues(question);
        return question;
      }));
  }

  private initFormControlValues(question) {
    this.content.setValue(question.content);
    console.log(question);
    if (this.questionRouterType == 'multipleChoice') {
      let answerKeys = this.getAnswerKeys(question);
      this.questionForm.setControl('choices',
        new FormArray(answerKeys.map(k =>
          this.fb.group({ content: question.answers[k] } as Choice))));
      const correctIndex = answerKeys.findIndex(x => x == question.correctAnswers);
      console.log(answerKeys, correctIndex);
      this.correct.setValue(correctIndex);
    } else
      this.correct.setValue(question.correctAnswer);
  }

  onSubmit() {
    this.submitting = true;
    const next = () => {
      this.submitting = false;
      this.router.navigate(['../../..'], { relativeTo: this.route });
    };
    let formValue = this.questionForm.value;
    formValue = formValue.questionType == 'multipleChoice' ?
      { ...formValue, choices: formValue.choices.map(c => c.content), }
      : { ...formValue, correctAnswer: formValue.correct };
    if (!this.qid) {
      this.nodeService.addQuestion(formValue)
        .subscribe(next);
    } else
      this.nodeService.updateQuestion(this.qid, formValue, this.questionRouterType)
        .subscribe(next)
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
    this.choices.push(this.fb.group(new Choice()));
  }

  get questionType() {
    return this.questionForm.get('questionType');
  }

  deleteChoice(index) {
    if (index === this.choices.length - 1 && index === this.correct.value) {
      this.correct.setValue(index - 1);
    }
    this.choices.removeAt(index);
  }

  // noinspection JSMethodCanBeStatic
  getAnswerKeys(question): string[] {
    return Object.keys(question.answers);
  }

  modify() {
    this.isModified = true;
  }
}

export class Choice {
  content = '';
}
