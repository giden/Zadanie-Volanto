import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Contact } from '../entity/contact'

@Injectable()
export class ContactsService {
    constructor(private http: Http) { }

    _url: string = 'http://localhost:9000/contacts/'

    getContacts() {
        return this.http.get(this._url)
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    getContact(id: number) {
        return this.http.get(this._url + id)
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    postContact(contact: Contact) {
        return this.http.post(this._url, JSON.stringify(contact), { 
                headers: new Headers({ 'Content-Type': 'application/json' }) 
            })
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    putContact(contact: Contact) {
        return this.http.put(this._url, JSON.stringify(contact), { 
                headers: new Headers({ 'Content-Type': 'application/json' }) 
            })
            .map((res: Response) => res.json())
            .catch(this.handleError);
    }

    deleteContact(id: number) {
        return this.http.delete(this._url + id)
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}