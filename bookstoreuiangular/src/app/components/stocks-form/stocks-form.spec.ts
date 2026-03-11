import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StocksForm } from './stocks-form';

describe('StocksForm', () => {
  let component: StocksForm;
  let fixture: ComponentFixture<StocksForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StocksForm],
    }).compileComponents();

    fixture = TestBed.createComponent(StocksForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
