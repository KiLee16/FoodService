import { state } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Register } from 'src/app/users/models/register';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  register: Register = {
    doj: new Date(),
    password: '',
    confirmPassword: '',
    email: '',
    username: '',
    roles: '',
    address: {
      houseno: 0,
      city: '',
      state: '',
      country: '',
      street: '',
      zip: 0,
    },
  };
  constructor() {}

  ngOnInit(): void {}

  registerSubmit() {
    console.log('hello from register submit');
    //to print the values which are collected from the register forms
    //rest call
  }
}
