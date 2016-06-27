import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { User } from '../entity/user'

@Injectable()
export class UsersService {
    constructor(private http: Http) { }

    _url: string = 'http://localhost:9000/users'

    getUsers() {
        return this.http.get(this._url)
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    getUser(id: number) {
        return this.http.get(this._url + '/' + id)
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    postUser(user: User) {
        return this.http.post(this._url, JSON.stringify(user), { 
                headers: new Headers({ 'Content-Type': 'application/json' }) 
            })
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    putUser(user: User) {
        return this.http.put(this._url, JSON.stringify(user), { 
                headers: new Headers({ 'Content-Type': 'application/json' }) 
            })
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    deleteUser(id: number) {
        return this.http.delete(this._url + '/' + id)
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}