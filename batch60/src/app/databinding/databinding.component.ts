import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-databinding',
  templateUrl: './databinding.component.html',
  styleUrls: ['./databinding.component.css'],
})
export class DatabindingComponent {
  userName: string = 'Allen';

  myClick() {
    console.log('Welcome to angular function');
  }

  EventFunction() {
    alert('event funtion fired');
  }
}
