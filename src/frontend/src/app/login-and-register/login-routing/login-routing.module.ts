import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { LoginAndRegisterComponent } from "../login-and-register.component";
import { AuthGuard } from "../../auth-guards";
import { ModifyComponent } from "../modify/modify.component";

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuard],
    data: { noAuth: true },
    children: [
      { path: 'login', component: LoginAndRegisterComponent },
      { path: 'register', component: LoginAndRegisterComponent },
    ],
  },
  {
    path: 'changePassword',
    canActivate: [AuthGuard],
    component: ModifyComponent,
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class LoginRoutingModule {
}
