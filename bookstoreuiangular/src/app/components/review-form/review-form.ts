import { Component, computed, inject, signal } from '@angular/core';
import { BooksService } from '../../services/books-service';
import { AuthorsService } from '../../services/authors-service';
import { ReviewsService } from '../../services/reviews-service';
import { ReviewForm } from '../../models/review-from';
import { form, FormField } from '@angular/forms/signals';
import { ActivatedRoute, Router } from '@angular/router';
import { MatAutocomplete, MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Author } from '../../models/author';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-review-form',
  imports: [FormField, AsyncPipe,MatInputModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,],
  templateUrl: './review-form.html',
  styleUrl: './review-form.css',
})
export class ReviewFormComponent {
  private booksService   = inject(BooksService);
  private authorsService = inject(AuthorsService);
  private reviewsService  = inject(ReviewsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  id : number = Number(this.route.snapshot.paramMap.get('id') ?? -1);
  book$ = this.booksService.book$;


  reviewModel = signal<ReviewForm>({
    contents: '',
    rating: 5,
    authorId: null,
    bookId: null,
  });

  authors$        = this.authorsService.authors$;
  selectedAuthor = signal<Author | null>(null);
  selectedAuthorId = computed(() => this.selectedAuthor()?.id ?? null);

  reviewForm = form(this.reviewModel);

  authorSearch = signal('');


  ngOnInit() {
    this.loadBook();
  }

  ngOnDestroy() {
    this.booksService.cleanBook();
  }

  loadBook(){
    this.booksService.getBookById(this.id);
    //TODO add check for not null
    this.reviewForm.bookId().value.set(this.id);
  }

  onAuthorSearch(value: string): void {
    this.authorSearch.set(value);
    this.authorsService.getAuthors(value);
  }

  onAuthorSelected(event: MatAutocompleteSelectedEvent): void {
    const author: Author = event.option.value;


    this.selectedAuthor.set(author);
    this.reviewForm.authorId().value.set(author.id);
    

    this.authorSearch.set('');
  }

  removeAuthor(author: Author): void {
    this.selectedAuthor.set(author);
    this.reviewForm.authorId().value.set(this.selectedAuthorId());
  }

  submitForm(): void {
    if (this.reviewForm().invalid()) return;
    //TODO add retunr to othe page sheesh and aga ugug ,lyat form validation
    this.reviewsService.addReview(this.reviewModel()).subscribe({
      next:  (data) => {
        console.log('Saved:', data);
        this.router.navigate(['/book', this.id], );
      },
      error: (err)  => console.error('Error:', err),
    });
  }


}
