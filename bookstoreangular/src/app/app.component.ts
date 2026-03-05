import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BookComponent } from "./components/book/book.component";
import { MainPageComponent } from "./components/main-page/main-page.component";
import { RouterShellComponent } from "./components/router-shell/router-shell.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, BookComponent, MainPageComponent, RouterShellComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bookstoreangular';
}
