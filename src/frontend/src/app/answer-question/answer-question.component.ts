import {Component, OnInit} from '@angular/core';
import {GetQuestionService} from "./get-question.service";
import {MultipleChoiceQuestion, ShortAnswerQuestion} from "./question";
import {Observable} from "rxjs/internal/Observable";

@Component({
  selector: 'app-answer-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.css']
})
export class AnswerQuestionComponent implements OnInit {

  question$: Observable<MultipleChoiceQuestion> | Observable<ShortAnswerQuestion>;
  id: number = 8;
  // type: string = 'multipleChoice';
  type: string = 'shortAnswer';

  // answer = this.question.answers;
  // content = this.question.content;

  constructor(private getQuestionService: GetQuestionService) {
  }

  ngOnInit() {
    this.question$ = this.getQuestionService.getQuestion(this.type, this.id)
  }


  getAnswerKeys(question): string[] {
    return Object.keys(question.answers);
  }

  onSubmit() {
  }
}
