import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-router-shell',
  imports: [RouterOutlet,RouterLink],
  templateUrl: './router-shell.html',
  styleUrl: './router-shell.css'
})
export class RouterShellComponent {

}
