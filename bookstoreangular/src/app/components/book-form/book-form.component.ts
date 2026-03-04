import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-book-form',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent {
  
  myForm! : FormGroup;
  
  constructor(){
    this.myForm = new FormGroup({
        "title": new FormControl("Book"),
    })
  }

  submit(){
      console.log(this.myForm);
  }
}
