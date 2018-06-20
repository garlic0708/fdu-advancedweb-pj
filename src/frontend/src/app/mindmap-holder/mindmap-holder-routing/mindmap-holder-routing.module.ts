import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { MindmapHolderComponent } from "../mindmap-holder.component";
import { AnswerQuestionComponent } from "../answer-question/answer-question.component";
import { ReleaseQuestionComponent } from "../release-question/release-question.component";
import { SidenavComponent } from "../sidenav/sidenav.component";
import { AuthGuard, RoleGuard } from "../../auth-guards";
import { UploadCourseComponent } from "../upload-course/upload-course.component";
import { UploadResourceComponent } from "../upload-resource/upload-resource.component";

const routes: Routes = [
  {
    path: '',
    component: SidenavComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'mindmap/:mapId',
        component: MindmapHolderComponent,
        children: [
          {
            path: '', // todo student role checker
            children: [
              { path: 'answer/:qType/:qid', component: AnswerQuestionComponent },
            ],
            canActivateChild: [RoleGuard],
            data: { role: 'STUDENT' },
          },
          {
            path: '', // todo teacher role checker
            children: [
              { path: 'question/:nodeId/new', component: ReleaseQuestionComponent },
              { path: 'question/:qType/:qid', component: ReleaseQuestionComponent },
              { path: 'courseware/:nodeId/new', component: UploadCourseComponent },
              { path: 'resource/:nodeId/new', component: UploadResourceComponent },
              { path: 'resource/:rid', component: UploadResourceComponent },
            ],
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
        ]
      },
    ],
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
export class MindmapHolderRoutingModule {
}
