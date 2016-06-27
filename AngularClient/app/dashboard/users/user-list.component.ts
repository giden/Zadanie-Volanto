import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UsersService } from '../../service/users.service' 
import { User } from '../../entity/user'


@Component({
    moduleId: module.id,
    providers: [UsersService],
    templateUrl: 'user-list.component.html',
})
export class UserListComponent {
    private users: User[];
    private error: boolean;
    private sub: any;

    constructor(private _service: UsersService, private router: Router) { }

    ngOnInit() {
        this.sub = this._service.getUsers().subscribe(
            data => this.users = <User[]>data,
            error => this.error = true)
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

}
