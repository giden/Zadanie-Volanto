import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthenticationService {

  constructor(private http: Http) {
  }

  login(username: String, password: String): any {
    return this.http.post('http://localhost:9000/auth/login', JSON.stringify({
      username: username,
      password: password
    }), {
        headers: new Headers({ 'Content-Type': 'application/json' })
      })
      .map((res: any) => {
        let data = res.json();
        sessionStorage.setItem('token', data.token);
        sessionStorage.setItem('role', data.role);
        sessionStorage.setItem('user', data.user);
      });
  }

  logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('role');
  }
}

export function isLogged() {
  return !!sessionStorage.getItem('token');
}
export function isAdmin() {
  let role = <string>sessionStorage.getItem('role');
  console.log('tets'+role);
  return role === 'ROLE_ADMIN';
}

