import { provideRouter, RouterConfig } from '@angular/router';

import { ContactsComponent } from './contacts.component'
import { ContactListComponent } from './contact-list.component'
import { ContactNewComponent } from './contact-new.component'

export const ContactsRoutes: RouterConfig = [
    {
        path: '',
        redirectTo: '/contacts',
        terminal: true
    },
    {
        path: 'contacts',
        component: ContactsComponent,
        children: [
            { path: '', component: ContactListComponent },
            { path: 'new', component: ContactNewComponent }
        ]
    }
];