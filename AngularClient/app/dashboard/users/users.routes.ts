import { provideRouter, RouterConfig } from '@angular/router';

import { UserListComponent } from './user-list.component'
import { AuthGuard } from '../../authAdmin.guard'

export const UsersRoutes: RouterConfig = [
    {
        path: 'users',
        canActivate: [AuthGuard],
        component: UserListComponent
    }
];