import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledRadovaComponent } from './pregled-radova.component';

describe('PregledRadovaComponent', () => {
  let component: PregledRadovaComponent;
  let fixture: ComponentFixture<PregledRadovaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PregledRadovaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PregledRadovaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
