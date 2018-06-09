import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from "rxjs/index";
import { Attachments } from "./attachment";

@Injectable({
  providedIn: 'root'
})
export class NodeService {

  attachmentUrl = '/api/node/attachments';

  constructor(private http: HttpClient) { }

  getAttachments(id): Observable<Attachments> {
    return this.http.get<Attachments>(`${this.attachmentUrl}/${id}`)
  }
}
