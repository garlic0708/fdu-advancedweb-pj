import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { MindmapHolderComponent } from "../mindmap-holder/mindmap-holder.component";

const routes: Routes = [
  // { path: 'mindmap/:id', component: MindmapHolderComponent },
  // todo
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule,
  ],
  declarations: []
})
export class AppRoutingModule {
}
