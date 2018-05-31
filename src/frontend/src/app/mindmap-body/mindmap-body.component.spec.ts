import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MindmapBodyComponent } from './mindmap-body.component';

describe('MindmapBodyComponent', () => {
  let component: MindmapBodyComponent;
  let fixture: ComponentFixture<MindmapBodyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MindmapBodyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MindmapBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
