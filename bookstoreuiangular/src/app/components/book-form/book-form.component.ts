import { Component, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';

@Component({
  selector: 'app-book-form',
  imports: [FormField],
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent {
  
  loginModel = signal({
    email: '',
    password: '',
  });

  loginForm = form(this.loginModel);

  
  constructor(){
  }

  submit(){
    //console.log(this.myForm);
  }
}
