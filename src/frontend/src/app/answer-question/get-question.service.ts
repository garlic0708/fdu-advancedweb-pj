import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MultipleChoiceQuestion, ShortAnswerQuestion} from "./question";
import {Observable} from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class GetQuestionService {

  multipleChoiceQuestionUrl = '/api/multipleChoiceQuestion';
  shortAnswerQuestionUrl = '/api/shortAnswerQuestion';

  constructor(private http: HttpClient) {
  }

  getMultipleChoiceQuestion(id): Observable<MultipleChoiceQuestion> {
    return this.http.get<MultipleChoiceQuestion>(`${this.multipleChoiceQuestionUrl}/${id}`)
  }

  getShortAnswerQuestion(id): Observable<ShortAnswerQuestion> {
    return this.http.get<ShortAnswerQuestion>(`${this.shortAnswerQuestionUrl}/${id}`)
  }

  getQuestion(type, id): Observable<any> {
    if (type == 'multipleChoice') {
      return this.getMultipleChoiceQuestion(id)
    }
    else if (type == 'shortAnswer') {
      return this.getShortAnswerQuestion(id)
    }
  }
}
