import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Review } from '../models/review';
import { Page } from '../models/page';
import { ReviewForm } from '../models/review-from';

@Injectable({
  providedIn: 'root',
})
export class ReviewsService {

  private http = inject(HttpClient);

  private reviewsByBookSubject = new BehaviorSubject<Review[]>([]);
  reviewsByBook$ = this.reviewsByBookSubject.asObservable();

  constructor() { }

  getReviewsByBookId(index: number, size : number, bookId : number) {
    const params = new HttpParams()
      .set('page', index.toString())
      .set('bookId', bookId.toString())
      .set('pageSize', size.toString());
    return this.http.get<Page<Review>>('/reviews/all', {params})
      .pipe(map(page => page.content.map(review =>{
        //review.publishDate = new Date(book.publishDate)
        return review;
      }))).subscribe({
        next: (reviews) => {
          this.reviewsByBookSubject.next(reviews);  // ← push new state
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.reviewsByBookSubject.next([]);
          //this.loadingSubject.next(false);
        }
      });
  }

  addReview(form : ReviewForm) : Observable<Review>  {
    const formData = new FormData();

    //TODO null checl
    formData.append('contents', form.contents);
    formData.append('rating', form.rating.toString());
    formData.append('authorId', form.authorId!.toString());
    formData.append('bookId', form.bookId!.toString());

    return this.http.post<Review>('/reviews/save', formData);
  }

}
