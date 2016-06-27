import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';

import { ContactsService } from '../../service/contacts.service'

@Component({
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ ContactsService ],
    template: '<router-outlet></router-outlet>',
})
export class ContactsComponent { }
