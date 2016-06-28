import {Component} from '@angular/core';
import {Router} from '@angular/router'
import {FORM_DIRECTIVES, FormBuilder, Validators, ControlGroup} from '@angular/common';
import {AuthenticationService} from './service/auth.service';

@Component({
    selector: 'login',
    template: `
  <div class="container">
    <form class="form-signin" #loginForm="ngForm" (submit)="onSubmit()">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" placeholder="User Name" required [(ngModel)]="username" autofocus ngControl="username">
        <input type="password" class="form-control" placeholder="Password" required [(ngModel)]="password" ngControl="password">
        <div *ngIf="error" class="alert alert-danger">
            Incorrect username or password
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" [disabled]="!loginForm.form.valid">Sign in</button>
    </form>
  </div>
  `
})

export class LoginComponent {
    username: string;
    password: string;

    error: boolean = false;

    constructor(public _auth: AuthenticationService, public router: Router) { }

    onSubmit() {
        this._auth.login(this.username, this.password).subscribe(
            (token: any) => this.router.navigate(['/contacts']),
            () => { this.error = true; }
        );
    }
}