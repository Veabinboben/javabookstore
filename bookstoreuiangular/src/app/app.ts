import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterShellComponent } from "./components/router-shell/router-shell.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterShellComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('bookstoreuiangular');
}
