import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page';
import { BookFormComponent } from './components/book-form/book-form';

export const routes: Routes = [
    {
        path: '',
        component: MainPageComponent,
    },
    {
        path: 'form',
        component: BookFormComponent,
    }
];
