import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page.component';
import { BookFormComponent } from './components/book-form/book-form.component';

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
