import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page';
import { BookFormComponent } from './components/book-form/book-form';
import { BookView } from './components/book-view/book-view';
import { ReviewFormComponent } from './components/review-form/review-form';

export const routes: Routes = [
    {
        path: '',
        component: MainPageComponent,
    },
    {
        path: 'form',
        component: BookFormComponent,
    },
    {
        path: 'book/:id',
        component: BookView,
    },
    {
        path: 'reviewAdd/:id',
        component: ReviewFormComponent,
    }
];
