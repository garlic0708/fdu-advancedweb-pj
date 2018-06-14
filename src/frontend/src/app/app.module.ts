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

import {HttpClientModule} from "@angular/common/http";

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
    DeleteDialogComponent
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
  ],
  providers: [
    environment.production ? [] : {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProviderService,
      multi: true,
    },

  ],
  bootstrap: [AppComponent],
  entryComponents: [DeleteDialogComponent]
})
export class AppModule {
}
