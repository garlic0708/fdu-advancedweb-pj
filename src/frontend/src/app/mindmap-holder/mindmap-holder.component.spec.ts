import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MindmapHolderComponent } from './mindmap-holder.component';

describe('MindmapHolderComponent', () => {
  let component: MindmapHolderComponent;
  let fixture: ComponentFixture<MindmapHolderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MindmapHolderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MindmapHolderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
