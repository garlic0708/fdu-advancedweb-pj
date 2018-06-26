import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { MockProviderService } from "./mock-provider.service";
import { environment } from "../environments/environment";

import { MaterialModule } from "./material/material.module";

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DeleteDialogComponent } from "./mindmap-holder/sidenav/sidenav.component";

import { HttpClientModule } from "@angular/common/http";
import { ColorPickerModule } from "ngx-color-picker";
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from "@angular/material";
import { AppRoutingModule } from "./app-routing/app-routing.module";
import { MindmapHolderModule } from "./mindmap-holder/mindmap-holder.module";
import { NavComponent } from "./nav/nav.component";
import { ModifyComponent } from "./login-and-register/modify/modify.component";
import { LoginAndRegisterModule } from "./login-and-register/login-and-register.module";
import { UploadCourseComponent } from "./mindmap-holder/upload-course/upload-course.component";
import { FileUploadModule } from "ng2-file-upload";
import { UploadResourceComponent } from "./mindmap-holder/upload-resource/upload-resource.component";

@NgModule({
  declarations: [
    AppComponent,
    DeleteDialogComponent,
    NavComponent,
    ModifyComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    // NoopAnimationsModule,
    ColorPickerModule,
    MindmapHolderModule,
    LoginAndRegisterModule,
    AppRoutingModule,
  ],
  providers: [
    environment.production ? [] : {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProviderService,
      multi: true,
    },
    { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 2500 } },
  ],
  bootstrap: [AppComponent],
  entryComponents: [DeleteDialogComponent]
})
export class AppModule {
}
