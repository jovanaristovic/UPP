import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UrednikNaucneComponent } from './urednik-naucne.component';

describe('UrednikNaucneComponent', () => {
  let component: UrednikNaucneComponent;
  let fixture: ComponentFixture<UrednikNaucneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UrednikNaucneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UrednikNaucneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
