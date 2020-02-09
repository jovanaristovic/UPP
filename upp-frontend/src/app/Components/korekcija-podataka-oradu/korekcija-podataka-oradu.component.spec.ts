import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KorekcijaPodatakaORaduComponent } from './korekcija-podataka-oradu.component';

describe('KorekcijaPodatakaORaduComponent', () => {
  let component: KorekcijaPodatakaORaduComponent;
  let fixture: ComponentFixture<KorekcijaPodatakaORaduComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KorekcijaPodatakaORaduComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KorekcijaPodatakaORaduComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
