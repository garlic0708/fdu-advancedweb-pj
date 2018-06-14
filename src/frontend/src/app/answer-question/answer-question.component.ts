import {Component, OnInit} from '@angular/core';
import {GetQuestionService} from "./get-question.service";
import {MultipleChoiceQuestion, ShortAnswerQuestion} from "./question";

@Component({
  selector: 'app-answer-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.css']
})
export class AnswerQuestionComponent implements OnInit {

  question: MultipleChoiceQuestion | ShortAnswerQuestion;
  id: number = 8;
  // type: string = 'multipleChoice';
  type: string = 'shortAnswer';

  // answer = this.question.answers;
  // content = this.question.content;

  constructor(private getQuestionService: GetQuestionService) {
  }

  ngOnInit() {
    if (this.type == 'multipleChoice') {
      this.getQuestionService.getMultipleChoiceQuestion(this.id)
        .subscribe(q => {
          console.log(q);
          this.question = q;
        });
    }
    else if (this.type == 'shortAnswer') {
      this.getQuestionService.getShortAnswerQuestion(this.id)
        .subscribe(q => this.question = q);
    }
  }


  get answerKeys(): string[] {
    return Object.keys(this.question.answers);
  }

  onSubmit() {
  }
}
