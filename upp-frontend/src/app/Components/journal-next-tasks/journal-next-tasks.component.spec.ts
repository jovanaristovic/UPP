import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalNextTasksComponent } from './journal-next-tasks.component';

describe('JournalNextTasksComponent', () => {
  let component: JournalNextTasksComponent;
  let fixture: ComponentFixture<JournalNextTasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JournalNextTasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JournalNextTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
