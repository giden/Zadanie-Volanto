import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ContactsService } from '../../service/contacts.service'
import { Contact } from '../../entity/contact'

@Component({
    moduleId: module.id,
    templateUrl: 'contact-list.component.html',
})
export class ContactListComponent implements OnInit {

    private contacts: Contact[];
    private error: boolean;
    private errorRemove: boolean;
    private sub: any;

    constructor(private _service: ContactsService, private router: Router) { }

    ngOnInit() {
        this.sub = this._service.getContacts().subscribe(
            data => this.contacts = <Contact[]>data,
            () => this.error = true)
    }


    deleteContact(id: number) {
        this.sub = this._service.deleteContact(id).subscribe(
            () => this.getContacts(),
            () => this.errorRemove = true);
    }

    getContacts() {
        this.sub = this._service.getContacts().subscribe(
            data => this.contacts = <Contact[]>data,
            () => this.error = true)
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
