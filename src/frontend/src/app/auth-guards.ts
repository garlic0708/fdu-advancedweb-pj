import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs/index";
import { CurrentUserService } from "./current-user.service";
import { map } from "rxjs/internal/operators";

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private currentUser: CurrentUserService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.currentUser.currentUser.pipe(
      map(user => {
        const noAuth = route.data['noAuth'];
        const check = noAuth ? !user : !!user;
        const redirect = noAuth ? '/app' : '/app/login';
        if (check) return true;
        this.router.navigate([redirect]);
        return false
      })
    );
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(childRoute, state);
  }

}

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivateChild {

  constructor(private currentUser: CurrentUserService) {
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.currentUser.currentUser.pipe(
      map(user => user.role == childRoute.data['role'])
    );
  }

}
