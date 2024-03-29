import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { login, registration } from '../../models';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent implements OnInit{

  // dependencies
  private fb = inject(FormBuilder)

  @Output() onSubmitLoginEvent = new EventEmitter()
  @Output() onSubmitRegisterEvent = new EventEmitter();

  // vars
  active: string = "login"
  username: string = ""
  password: string = ""
  loginForm !: FormGroup
  registerForm !: FormGroup

  // lifecycle hooks
  ngOnInit(): void {
    this.loginForm = this.createLoginForm()
    this.registerForm = this.createRegisterForm()
  }

  // methods
  onLoginTab(): void {
		this.active = "login";
	}

	onRegisterTab(): void {
		this.active = "register";
	}

  onSubmitLogin() {
    this.onSubmitLoginEvent.emit(this.loginForm.value as login)
  }

  // onSubmitRegister() {
  //   this.onSubmitRegisterEvent.emit({"username": this.username, "name": this.name, "password": this.password})
  // }

  onSubmitRegister() {
    console.log('REGISTER FORM: ', this.registerForm.value as registration)
    this.onSubmitRegisterEvent.emit(this.registerForm.value as registration)
  }

  createLoginForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required])
    })
  }

  createRegisterForm() : FormGroup {
    return this.fb.group({
      username: this.fb.control('', [Validators.required]),
      firstname: this.fb.control('', [Validators.required]),
      lastname: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.email]),
      password: this.fb.control('', [Validators.required])
    })
  }

}
