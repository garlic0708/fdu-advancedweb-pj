import { Component, OnInit } from '@angular/core';
import { MultipleChoiceQuestion, ShortAnswerQuestion } from "./question";
import { Observable } from "rxjs/internal/Observable";
import { NodeService } from "../node.service";
import { ActivatedRoute, Router } from "@angular/router";
import { map, switchMap } from "rxjs/internal/operators";

@Component({
  selector: 'app-answer-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.css']
})
export class AnswerQuestionComponent implements OnInit {

  question$: Observable<MultipleChoiceQuestion> | Observable<ShortAnswerQuestion>;
  id: number;
  // type: string = 'multipleChoice';
  type: string;
  answer: string;
  submitting: boolean = false;

  // answer = this.question.answers;
  // content = this.question.content;

  constructor(private nodeService: NodeService,
              private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.question$ = this.route.paramMap.pipe(switchMap(params => {
      this.type = params.get('qType');
      this.id = +params.get('qid');
      return this.nodeService.getQuestion(this.type, this.id).pipe(map(q => {
        this.answer = q.answer;
        return q;
      }))
    }))
  }


  getAnswerKeys(question): string[] {
    return Object.keys(question.answers);
  }

  onSubmit() {
    this.submitting = true;
    this.nodeService.answerQuestion(this.id, this.answer, this.type)
      .subscribe(() => {
        this.submitting = false;
        this.router.navigate(['../../..'], { relativeTo: this.route })
      })
  }
}
