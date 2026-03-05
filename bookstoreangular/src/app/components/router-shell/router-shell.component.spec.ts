import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RouterShellComponent } from './router-shell.component';

describe('RouterShellComponent', () => {
  let component: RouterShellComponent;
  let fixture: ComponentFixture<RouterShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterShellComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RouterShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
