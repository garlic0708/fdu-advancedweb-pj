import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MindmapBodyComponent } from './mindmap-body/mindmap-body.component';

@NgModule({
  declarations: [
    AppComponent,
    MindmapBodyComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
