import { provideRouter, RouterConfig } from '@angular/router';

import { UserListComponent } from './user-list.component'

export const UsersRoutes: RouterConfig = [
    {
        path: 'users',
        component: UserListComponent
    }
];