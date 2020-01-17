import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalsAdminComponent } from './journals-admin.component';

describe('JournalsAdminComponent', () => {
  let component: JournalsAdminComponent;
  let fixture: ComponentFixture<JournalsAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JournalsAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JournalsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
