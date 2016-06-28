import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { isAdmin } from './service/auth.service';

@Injectable()
export class AuthAdminGuard implements CanActivate {
    constructor(private router: Router) { }

    canActivate() {
        if (isAdmin()) { return true; }
        this.router.navigate(['/login']);
        return false;
    }
}