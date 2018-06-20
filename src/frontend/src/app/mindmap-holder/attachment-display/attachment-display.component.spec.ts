import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttachmentDisplayComponent } from './attachment-display.component';

describe('AttachmentDisplayComponent', () => {
  let component: AttachmentDisplayComponent;
  let fixture: ComponentFixture<AttachmentDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttachmentDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachmentDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
