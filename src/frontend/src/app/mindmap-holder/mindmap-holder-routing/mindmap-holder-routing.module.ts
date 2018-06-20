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
    path: 'app',
    component: SidenavComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'mindmap/:mapId',
        component: MindmapHolderComponent,
        children: [
          // {
          //   path: '', // todo student role checker
          //   children: [
          {
            path: 'answer/:qType/:qid', component: AnswerQuestionComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'STUDENT' },
          },
          // ],
          // canActivateChild: [RoleGuard],
          // data: { role: 'STUDENT' },
          // },
          // {
          //   path: '', // todo teacher role checker
          //   children: [
          {
            path: 'question/:nodeId/new', component: ReleaseQuestionComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
          {
            path: 'question/:qType/:qid', component: ReleaseQuestionComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
          {
            path: 'courseware/:nodeId/new', component: UploadCourseComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
          {
            path: 'resource/:nodeId/new', component: UploadResourceComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
          {
            path: 'resource/:rid', component: UploadResourceComponent,
            canActivateChild: [RoleGuard],
            data: { role: 'TEACHER' },
          },
          // ],
          // canActivateChild: [RoleGuard],
          // data: { role: 'TEACHER' },
          // },
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
