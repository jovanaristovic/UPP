import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificFieldComponent } from './scientific-field.component';

describe('ScientificFieldComponent', () => {
  let component: ScientificFieldComponent;
  let fixture: ComponentFixture<ScientificFieldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificFieldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
