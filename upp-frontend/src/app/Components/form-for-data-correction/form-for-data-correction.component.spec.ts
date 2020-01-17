import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormForDataCorrectionComponent } from './form-for-data-correction.component';

describe('FormForDataCorrectionComponent', () => {
  let component: FormForDataCorrectionComponent;
  let fixture: ComponentFixture<FormForDataCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormForDataCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormForDataCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
