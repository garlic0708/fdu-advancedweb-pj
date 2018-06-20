import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestionComponent } from './answer-question.component';

describe('AnswerQuestionComponent', () => {
  let component: AnswerQuestionComponent;
  let fixture: ComponentFixture<AnswerQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
