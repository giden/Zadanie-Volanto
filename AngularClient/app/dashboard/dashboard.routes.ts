import { provideRouter, RouterConfig } from '@angular/router';

import { DashboardComponent } from './dashboard.component'
import { ContactsRoutes } from './contacts/contacts.routes'
import { UsersRoutes } from './users/users.routes'
import { AuthGuard } from '../auth.guard'

export const DashboardRoutes: RouterConfig = [
    {
        path: '',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        children: [
            ...ContactsRoutes,
            ...UsersRoutes
        ]
    }
];