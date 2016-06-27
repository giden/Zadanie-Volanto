import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ContactsService } from '../../service/contacts.service'
import { Contact } from '../../entity/contact'

@Component({
    moduleId: module.id,
    templateUrl: 'contact-new.component.html',
})
export class ContactNewComponent implements OnInit, OnDestroy {

    private contact: Contact = new Contact();
    private error: boolean;
    private subParam: any;
    private sub: any;
    private active: boolean = true;

    constructor(private route: ActivatedRoute, private router: Router, private _service: ContactsService) { }

    ngOnInit() {
        this.getContact()
    }

    load() {
        this.getContact()
        this.active = false;
        setTimeout(() => this.active = true, 0);
    }

    getContact() {
        this.subParam = this.route.params.subscribe(params => {
            let id = +params['id'];
            if (id) {
                this.sub = this._service.getContact(id).subscribe(
                    contact => this.contact = <Contact>contact
                );
            }
        });
    }

    onSubmit() {
        if (this.contact.id) {
            this._service.putContact(this.contact).subscribe(
                data => this.router.navigate(['/contacts']),
                err => this.error = true
            );
        } else {
            this._service.postContact(this.contact).subscribe(
                data => this.router.navigate(['/contacts']),
                err => this.error = true
            );
        }
    }

    ngOnDestroy() {
        this.subParam.unsubscribe();
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }
}
