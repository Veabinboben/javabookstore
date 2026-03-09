import { Component, computed, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { form, FormField, required } from '@angular/forms/signals';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ActivatedRoute, Router } from '@angular/router';
import { WarehousesService } from '../../services/warehouses-service';
import { BooksService } from '../../services/books-service';
import { StocksService } from '../../services/stocks-service';
import { Warehouse } from '../../models/warehouse';
import { StockForm } from '../../models/stock-form';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-stocks-form',
  imports: [FormField, AsyncPipe, MatInputModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,],
  templateUrl: './stocks-form.html',
  styleUrl: './stocks-form.css',
})
export class StocksForm implements OnInit, OnDestroy {
  private readonly booksService = inject(BooksService);
  private readonly warehousesService = inject(WarehousesService);
  private readonly stocksService = inject(StocksService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  id: number = Number(this.route.snapshot.paramMap.get('id') ?? -1);
  book$ = this.booksService.book$;


  stockModel = signal<StockForm>({
    stock: 0,
    warehouseId: null,
    bookId: null,
  });

  warehouses$ = this.warehousesService.warehouses$;
  selectedWarehouse = signal<Warehouse | null>(null);
  selectedWarehouseId = computed(() => this.selectedWarehouse()?.id ?? null);

  stockForm = form(this.stockModel, (schemaPath) => {
    required(schemaPath.stock, { message: 'Stock is required' });
    required(schemaPath.warehouseId, { message: 'Warehouse is required' });
  });

  warehouseSearch = signal('');


  ngOnInit() {
    this.loadBook();
  }

  ngOnDestroy() {
    this.booksService.cleanBook();
  }

  loadBook() {
    this.booksService.getBookById(this.id);
    this.stockForm.bookId().value.set(this.id);
  }

  onWarehouseSearch(value: string): void {
    this.warehouseSearch.set(value);
    this.warehousesService.getWarehouses(value);
  }

  onWarehouseSelected(event: MatAutocompleteSelectedEvent): void {
    const warehouse: Warehouse = event.option.value;

    this.selectedWarehouse.set(warehouse);
    this.stockForm.warehouseId().value.set(warehouse.id);

    this.warehouseSearch.set('');
  }

  removeWarehouse(warehouse: Warehouse): void {
    this.selectedWarehouse.set(warehouse);
    this.stockForm.warehouseId().value.set(this.selectedWarehouseId());
  }

  submitForm(): void {
    if (this.stockForm().invalid()) return;
    this.stocksService.addStock(this.stockModel()).subscribe({
      next: (data) => {
        console.log('Saved:', data)
        this.router.navigate(['/book', this.id],);
      },
      error: (err) => console.error('Error:', err),
    });
  }
}
