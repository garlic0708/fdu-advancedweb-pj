import {Component, OnInit} from '@angular/core';
import {mockData} from "../../assets/mock-data";

@Component({
  selector: 'app-answer-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.css']
})
export class AnswerQuestionComponent implements OnInit {

  question = mockData['GET /api/question/:qid'];
  answer = this.question.answers;
  content = this.question.content;

  constructor() {
  }


  ngOnInit() {
  }

  get answerKeys(): string[] {
    return Object.keys(this.question.answers);
  }

  onSubmit() {
  }
}
