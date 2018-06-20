import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleaseQuestionComponent } from './release-question.component';

describe('ReleaseQuestionComponent', () => {
  let component: ReleaseQuestionComponent;
  let fixture: ComponentFixture<ReleaseQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleaseQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
