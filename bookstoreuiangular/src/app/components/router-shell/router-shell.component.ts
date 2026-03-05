import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-router-shell',
  imports: [RouterOutlet,RouterLink],
  templateUrl: './router-shell.component.html',
  styleUrl: './router-shell.component.css'
})
export class RouterShellComponent {

}
