import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthenticationService {
  token: string;
  user: string;

  constructor(private http: Http) {
    this.token = sessionStorage.getItem('token');
  }

  login(username: String, password: String): any {
    return this.http.post('/auth/login', JSON.stringify({
      username: username,
      password: password
    }), {
        headers: new Headers({ 'Content-Type': 'application/json' })
      })
      .map((res: any) => {
        let data = res.json();
        this.token = data.token;
        this.user = data.user;
        localStorage.setItem('token', this.token);
        localStorage.setItem('user', this.user);
      });
  }

  logout() {
    this.token = undefined;
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');

    return Observable.of(true);
  }
}

export function isLogged() {
  return !!sessionStorage.getItem('token');
}
export function isAdmin() {
  let role = <string>sessionStorage.getItem('role');
  return role === 'ROLE_ADMIN';
}

