import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { Attachments } from "./attachment";
import { MindMapNode } from "./mind-map-node";
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NodeService {

  attachmentUrl = '/api/node/attachments';
  mapUrl = '/api/node/getAll';
  manipulateUrl = '/api/mindmap/manipulate';

  constructor(private http: HttpClient) {
  }

  getAttachments(id): Observable<Attachments> {
    return this.http.get<Attachments>(`${this.attachmentUrl}/${id}`)
  }

  getMap(mapId) {
    return this.http.get<MindMapNode>(`${this.mapUrl}/${mapId}`)
      .pipe(map(data => NodeService.transform(data)))
  }

  manipulate(mapId, manipulations) {
    return this.http.post(`${this.manipulateUrl}/${mapId}`, manipulations)
  }

  static transform(root: MindMapNode) {
    function transformRecursive(root: MindMapNode) {
      return {
        id: root.internalId,
        title: root.name,
        attr: root.color ? {
          style: {background: root.color},
        } : {},
        ideas: root.childNodes.reduce((acc, cur, i) => {
          acc[(i / 2 + 1) * -(i % 2 * 2 - 1)] = transformRecursive(cur);
          return acc
        }, {}),
      }
    }

    return {
      id: 'root',
      formatVersion: 3,
      ideas: {
        "1": transformRecursive(root),
      },
    }
  }
}
