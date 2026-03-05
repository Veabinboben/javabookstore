import { Component } from '@angular/core';

@Component({
  selector: 'app-book-form',
  imports: [FormField],
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
