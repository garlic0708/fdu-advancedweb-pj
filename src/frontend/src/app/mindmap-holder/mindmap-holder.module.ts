import { NgModule } from '@angular/core';
import { ReleaseQuestionComponent } from "./release-question/release-question.component";
import { AnswerQuestionComponent } from "./answer-question/answer-question.component";
import { MindmapHolderComponent } from "./mindmap-holder.component";
import { PieChartComponent } from "./pie-chart/pie-chart.component";
import { MindmapBodyModule } from "../mindmap-body/mindmap-body.module";
import { MindmapHolderRoutingModule } from "./mindmap-holder-routing/mindmap-holder-routing.module";
import { HttpClientModule } from "@angular/common/http";
import { MaterialModule } from "../material/material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ColorPickerModule } from "ngx-color-picker";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { BrowserModule } from "@angular/platform-browser";
import { AttachmentDisplayComponent } from './attachment-display/attachment-display.component';
import { SidenavComponent } from "./sidenav/sidenav.component";
import { UploadResourceComponent } from "./upload-resource/upload-resource.component";
import { UploadCourseComponent } from "./upload-course/upload-course.component";
import { FileUploadModule } from "ng2-file-upload";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    // NoopAnimationsModule,
    ColorPickerModule,
    FileUploadModule,
    MindmapBodyModule,
    MindmapHolderRoutingModule,
  ],
  declarations: [
    MindmapHolderComponent,
    ReleaseQuestionComponent,
    AnswerQuestionComponent,
    PieChartComponent,
    AttachmentDisplayComponent,
    SidenavComponent,
    UploadResourceComponent,
    UploadCourseComponent
  ],
})
export class MindmapHolderModule { }
