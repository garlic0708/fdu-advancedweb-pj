import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadCourceComponent } from './upload-cource.component';

describe('UploadCourceComponent', () => {
  let component: UploadCourceComponent;
  let fixture: ComponentFixture<UploadCourceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadCourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadCourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
