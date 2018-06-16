import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import {AppComponent} from './app.component';
import {MindmapBodyComponent} from './mindmap-body/mindmap-body.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {MockProviderService} from "./mock-provider.service";
import {environment} from "../environments/environment";

import { MindmapHolderComponent } from './mindmap-holder/mindmap-holder.component';
import { MaterialModule } from "./material/material.module";

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NoopAnimationsModule } from "@angular/platform-browser/animations";

import {ReleaseQuestionComponent} from './release-question/release-question.component';
import {PieChartComponent} from "./pie-chart/pie-chart.component";
import { SidenavComponent } from './sidenav/sidenav.component';
import { DeleteDialogComponent } from "./sidenav/sidenav.component";
import { AnswerQuestionComponent } from "./answer-question/answer-question.component";

import { HttpClientModule } from "@angular/common/http";
import { ColorPickerModule } from "ngx-color-picker";
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from "@angular/material";
import { NavComponent } from './nav/nav.component';
import { ModifyComponent } from './modify/modify.component';
import { UploadCourceComponent } from './upload-cource/upload-cource.component';

import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';
import { UploadResourceComponent } from './upload-resource/upload-resource.component';
import { AppRoutingModule} from "./app-routing.module";
import {HttpModule, JsonpModule} from "@angular/http";
import { GithubComponent } from './github/github.component';
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    AppComponent,
    PieChartComponent,
    MindmapBodyComponent,
    LoginComponent,
    RegisterComponent,
    MindmapHolderComponent,
    ReleaseQuestionComponent,
    AnswerQuestionComponent,
    SidenavComponent,
    AnswerQuestionComponent,
    DeleteDialogComponent,
    NavComponent,
    ModifyComponent,
    UploadCourceComponent,
    UploadResourceComponent,
    GithubComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule,
    HttpClientModule,
    // NoopAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    ColorPickerModule,
    CommonModule,
    FileUploadModule,
    AppRoutingModule,
    RouterModule
  ],
  providers: [
    environment.production ? [] : {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProviderService,
      multi: true,
    },
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500}},

  ],
  bootstrap: [AppComponent],
  entryComponents: [DeleteDialogComponent]
})
export class AppModule {
}
