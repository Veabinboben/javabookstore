import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BooksService } from '../../services/books-service';
import { AsyncPipe, CommonModule } from '@angular/common';
import { BookComponent } from "../book/book";
import { ReviewsService } from '../../services/reviews-service';
import { MatCard, MatCardHeader, MatCardTitleGroup, MatCardContent, MatCardTitle, MatCardSubtitle } from '@angular/material/card';
import { StocksService } from '../../services/stocks-service';
import { WarehousesService } from '../../services/warehouses-service';
@Component({
  selector: 'app-book-view',
  imports: [CommonModule, BookComponent,MatCard, MatCardHeader, MatCardTitleGroup,MatCardContent,MatCardTitle, MatCardSubtitle],
  templateUrl: './book-view.html',
  styleUrl: './book-view.css',
})
export class BookView {
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private booksService = inject(BooksService);
  private stocksService = inject(StocksService);
  private warehousesService = inject(WarehousesService);
  private reviewsService = inject(ReviewsService);
  id : number = Number(this.route.snapshot.paramMap.get('id') ?? -1);
  
  book$ = this.booksService.book$;
  reviews$ = this.reviewsService.reviewsByBook$;
  stocks$ = this.stocksService.stocks$;
  

  length = 100; 
  pageSize = 10; 
  pageIndex = 0; 

  ngOnInit() {
    this.loadBook();
    this.loadReviews();
    this.loadStocks();
  }

  ngOnDestroy() {
    this.booksService.cleanBook();
    this.reviewsService.cleanReviews();
    //TODO
  }

  loadBook(){
    this.booksService.getBookById(this.id);
  }

  loadReviews(){
    this.reviewsService.getReviewsByBookId(this.pageIndex,this.pageSize,this.id);
  }
  
  loadStocks(){
    this.stocksService.getStocks(this.id);
  }

  addReview(){
    this.router.navigate(['/review/add', this.id]);
  } 
  addStock(){
    this.router.navigate(['/stocks/add', this.id]);
  } 
}
