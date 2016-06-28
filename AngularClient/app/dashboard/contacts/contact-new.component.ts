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
                    data => this.contact = <Contact>data
                );
            }
        });
    }

    onSubmit() {
        if (this.contact.id) {
            this._service.putContact(this.contact).subscribe(
                () => this.router.navigate(['/contacts']),
                () => this.error = true
            );
        } else {
            this._service.postContact(this.contact).subscribe(
                () => this.router.navigate(['/contacts']),
                () => this.error = true
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
