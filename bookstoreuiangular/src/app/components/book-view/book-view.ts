import { AfterViewInit, Component, ElementRef, inject, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { BooksService } from '../../services/books-service';
import { CommonModule } from '@angular/common';
import { BookComponent } from "../book/book";
import { ReviewsService } from '../../services/reviews-service';
import { MatCard, MatCardHeader, MatCardTitleGroup, MatCardContent, MatCardTitle, MatCardSubtitle } from '@angular/material/card';
import { StocksService } from '../../services/stocks-service';
import { WarehousesService } from '../../services/warehouses-service';
import { Book } from '../../models/book';

@Component({
  selector: 'app-book-view',
  imports: [CommonModule, BookComponent, MatCard, MatCardHeader, MatCardTitleGroup, MatCardContent, MatCardTitle, MatCardSubtitle],
  templateUrl: './book-view.html',
  styleUrl: './book-view.css',
})
export class BookView implements OnInit, AfterViewInit, OnDestroy {
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly booksService = inject(BooksService);
  private readonly stocksService = inject(StocksService);
  private readonly reviewsService = inject(ReviewsService);
  id: number = Number(this.route.snapshot.paramMap.get('id') ?? -1);

  book$ = this.booksService.book$;
  reviews$ = this.reviewsService.reviewsByBook$;
  stocks$ = this.stocksService.stocks$;

  pageSize = 10;
  pageIndex = 0;

  @ViewChild('anchor', { static: true }) anchor!: ElementRef<HTMLElement>;
  private observer!: IntersectionObserver;


  ngOnInit() {
    this.loadBook();
    this.loadReviews();

    this.loadStocks();
  }

  ngAfterViewInit() {
    this.observer = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting) {
        this.appendReviws() // call service to fetch and append
      }
    }, { root: null, rootMargin: '200px', threshold: 0 });

    if (this.anchor) this.observer.observe(this.anchor.nativeElement);
  }

  ngOnDestroy() {
    this.booksService.cleanBook();
    this.reviewsService.cleanReviews();
  }

  loadBook() {
    this.booksService.getBookById(this.id);
  }

  loadReviews() {
    this.reviewsService.getReviewsByBookId(this.pageIndex, this.pageSize, this.id);
  }

  appendReviws() {
    this.pageIndex += 1;
    this.reviewsService.appendReviewsByBookId(this.pageIndex, this.pageSize, this.id);
  }

  loadStocks() {
    this.stocksService.getStocks(this.id);
  }

  addReview() {
    this.router.navigate(['/review/add', this.id]);
  }

  addStock() {
    this.router.navigate(['/stocks/add', this.id]);
  }

  goToBookToEdit(book: Book) {
    const navigationExtras: NavigationExtras = {
      state: {
        data: book
      }
    };
    this.router.navigate(['/form'], navigationExtras);
  }

  deleteBook() {
    this.booksService.deleteBook(this.id).subscribe({
      next: () => {
        this.router.navigate(['/all'], { queryParams: { page: 0 } });
      }
    });;
  }
}
