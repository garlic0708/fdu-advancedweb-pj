import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, forkJoin, Observable, of, Subject } from "rxjs/index";
import { Attachments, Question, Resource } from "./attachment";
import { MindMapNode } from "./mind-map-node";
import { map } from 'rxjs/operators';
import { MultipleChoiceQuestion, ShortAnswerQuestion } from "./answer-question/question";
import flatMap = require('lodash/flatMap');
import concat = require('lodash/concat')
import { mergeMap } from "rxjs/internal/operators";
import { FileUploader } from "ng2-file-upload";

@Injectable({
  providedIn: 'root'
})
export class NodeService {

  private attachmentUrl = '/api/node/attachments';
  private mapUrl = '/api/node/getAll';
  private manipulateUrl = '/api/mindmap/manipulate';

  private addChoiceUrl = '/api/homework/addMC';
  private addAnswerUrl = '/api/homework/addSA';
  private getChoiceUrl = '/api/homework/getMC';
  private getAnswerUrl = '/api/homework/getSA';
  private updateChoiceUrl = '/api/homework/updateMC';
  private updateAnswerUrl = '/api/homework/updateSA';
  private answerChoiceUrl = "/api/homework/addMCA";
  private answerAnswerUrl = "/api/homework/addSAA";
  private getChoiceAnswerUrl = '/api/homework/getMCA';
  private uploadCoursewareUrl = '/api/upload/courseware';
  private uploadResourceUrl = '/api/upload/resource';
  private addResourceUrl = '/api/resource/add';
  private getCoursewareUrl = '/api/file/courseware/get';
  private getFileResourceUrl = '/api/file/resource/get';
  private getUrlResourceUrl = '/api/resource/get';
  private updateResourceUrl = '/api/resource/update';

  private activeAttachments: Subject<Attachments> = null;
  private activeNodeId: number = null; // Stores node internal id that is currently active
  private _activeAttachments: Attachments;

  // Stores nodes already persisted (node internal id -> actual persistence id)
  private activeNodeMap: Map<number, number> = new Map();

  // The cache is used for addition of attachments to
  // nodes that are not yet persisted

  // cachedAttachments stores the snapshot of the nodes to which items are newly added (node id -> attachments)
  private cachedAttachments: Map<number, Attachments> = new Map();

  // cachedItem stores the post body of newly added attachments (temp attachment id -> full attachment)
  private cachedItem = new Map();

  private nextCount = (function () {
    let counter = -1;
    return function () {
      return counter--;
    }
  })();

  constructor(private http: HttpClient) {
  }

  getAttachments(id): Observable<Attachments> {
    console.log(id, this.activeNodeId);
    if (id != this.activeNodeId) {
      this.activeNodeId = id;
      // re-assigning will make the previous subject automatically garbage collected
      if (this.activeNodeMap.has(id)) {
        this.activeAttachments = new Subject();
        this.fetchAttachments(id);
      } else {
        this._activeAttachments = this.cachedAttachments.get(id) ||
          { questions: [], resources: [], coursewares: [] };
        console.log(this._activeAttachments);
        this.activeAttachments = new BehaviorSubject(this._activeAttachments)
      }
    }
    return this.activeAttachments
  }

  private fetchAttachments(id) {
    this.http.get<Attachments>(`${this.attachmentUrl}/${this.activeNodeMap.get(id)}`)
      .subscribe(value => {
        console.log(this.activeNodeId, id);
        if (this.activeNodeId == id) {
          this._activeAttachments = value;
          this.activeAttachments.next(this._activeAttachments)
        }
      });
  }

  getMultipleChoiceQuestion(id): Observable<MultipleChoiceQuestion> {
    if (!this.cachedItem.has(id)) return this.http.get<MultipleChoiceQuestion>(`${this.getChoiceUrl}/${id}`);
    else {
      const item = this.cachedItem.get(id);
      return of({
        answers: item.choices.reduce((acc, cur, i) => {
          acc[i.toString()] = cur;
          return acc
        }, {}),
        ...item
      })
    }
  }

  getShortAnswerQuestion(id): Observable<ShortAnswerQuestion> {
    return !this.cachedItem.has(id) ? this.http.get<ShortAnswerQuestion>(`${this.getAnswerUrl}/${id}`)
      : of(this.cachedItem.get(id))
  }

  getQuestion(type, id): Observable<any> {
    if (type == 'multipleChoice') {
      return this.getMultipleChoiceQuestion(id)
    }
    else if (type == 'shortAnswer') {
      return this.getShortAnswerQuestion(id)
    }
  }

  updateQuestion(id, questionForm, type): Observable<any> {
    if (this.cachedItem.has(id))
      return of(Object.assign(this.cachedItem.get(id), questionForm));
    else
      return this.http.post(`${type == 'multipleChoice' ? this.updateChoiceUrl : this.updateAnswerUrl}/${id}`,
        questionForm)
  }

  answerQuestion(id, answer, type): Observable<any> {
    return this.http.post(`${type == 'multipleChoice' ? this.answerChoiceUrl : this.answerAnswerUrl}/${id}`,
      { answer })
  }

  getMultipleChoiceAnswers(id): Observable<any> {
    return this.http.get(`${this.getChoiceAnswerUrl}/${id}`)
  }

  private postObservable(form, type, nodeId, pipe?): Observable<any> {
    let observable;
    if (type == 'question') {
      const { questionType, ...rest } = form;
      const url = questionType == 'multipleChoice' ? this.addChoiceUrl : this.addAnswerUrl;
      observable = this.http.post<Question>(`${url}/${nodeId}`, rest)
    }
    return pipe ? observable.pipe(map(pipe)) : observable;
  }

  addQuestion(questionForm): Observable<Question> {
    if (this.activeNodeMap.has(this.activeNodeId)) {
      const activeNodeId = this.activeNodeId;
      return this.postObservable(questionForm, 'question', this.activeNodeMap.get(this.activeNodeId))
        .pipe(map(q => {
          if (this.activeAttachments && activeNodeId == this.activeNodeId) {
            this._activeAttachments.questions.push(q);
            this.activeAttachments.next(this._activeAttachments)
          }
          return q
        }));
    } else {
      const id = this.nextCount();
      this.cachedItem.set(id, questionForm);
      if (!this.cachedAttachments.has(this.activeNodeId)) this.cachedAttachments.set(this.activeNodeId, this._activeAttachments);
      this._activeAttachments.questions.push({
        id,
        type: questionForm.questionType,
        description: questionForm.content
      });
      this.activeAttachments.next(this._activeAttachments);
      return of(null)
    }
  }

  getMap(mapId) {
    this.activeNodeId = null;
    return this.http.get<MindMapNode>(`${this.mapUrl}/${mapId}`)
      .pipe(map(data => this.transform(data)))
  }

  private mapObservables(newMap: Map<number, number>) {
    console.log(this.cachedAttachments);
    return flatMap(Array.from(newMap.entries()), (entry) => {
      console.log(entry);
      const attachments: Attachments = this.cachedAttachments.get(entry[0]);
      return concat(
        attachments.questions.map(q =>
          this.postObservable(this.cachedItem.get(q.id), 'question', entry[1], newQ => {
            q.id = newQ.id // Update current active node, for the id should be updated
          })),
      );
    })
  }

  manipulate(mapId, manipulations): Observable<any> {
    const observable = this.http.post<MindMapNode>(`${this.manipulateUrl}/${mapId}`, manipulations);
    if (!this.cachedAttachments.size) return observable.pipe(map(root => this.createNewMap(root)));
    return observable.pipe(mergeMap(root => {
        const sources = this.mapObservables(this.createNewMap(root));
        console.log(sources, 'pipe 1');
        return forkJoin(sources);
      }),
      map(() => {
        console.log('pipe 2', this._activeAttachments);
        if (this.cachedAttachments.has(this.activeNodeId))
          this.activeAttachments.next(this._activeAttachments);
        this.cachedAttachments.clear();
        this.cachedItem.clear()
      }))
  }

  private createNewMap(root: MindMapNode): Map<number, number> {
    console.log('create new map');
    const newMap = new Map();
    const recursive = (node: MindMapNode) => {
      if (!this.activeNodeMap.has(node.internalId)) {
        this.activeNodeMap.set(node.internalId, node.id);
        newMap.set(node.internalId, node.id);
      }
      node.childNodes.forEach(child => recursive(child))
    };
    recursive(root);
    return newMap
  }

  private transform(root: MindMapNode) {
    this.activeNodeMap.clear();
    this.cachedAttachments.clear();
    this.cachedItem.clear();
    const transformRecursive = (root: MindMapNode) => {
      this.activeNodeMap.set(root.internalId, root.id);
      return {
        id: root.internalId,
        title: root.name,
        attr: root.color ? {
          style: { background: root.color },
        } : {},
        ideas: root.childNodes.reduce((acc, cur, i) => {
          acc[(i / 2 + 1) * -(i % 2 * 2 - 1)] = transformRecursive(cur);
          return acc
        }, {}),
      }
    };

    return {
      id: 'root',
      formatVersion: 3,
      ideas: {
        "1": transformRecursive(root),
      },
    }
  }

  isNodeActive(id) {
    return this.activeNodeMap.has(id)
  }

  getUploader(type): FileUploader {
    console.log(this.activeNodeMap, this.activeNodeId);
    return new FileUploader({
      url: type == 'courseware' ? this.uploadCoursewareUrl :
        this.uploadResourceUrl,
      method: "POST",
      isHTML5: true
    })
  }

  uploadItemForm(uploader, id) {
    return (_, form) => {
      form.append('nodeId', this.activeNodeMap.get(id));
      uploader.uploadAll()
    }
  }

  uploadSuccess(type, response, id) {
    console.log(this._activeAttachments, response);
    if (this.activeNodeId == id) {
      (type == 'courseware' ? this._activeAttachments.coursewares : this._activeAttachments.resources)
        .push(JSON.parse(response));
      this.activeAttachments.next(this._activeAttachments);
    }
  }

  getFile(type, id): Observable<Blob> {
    return this.http.get<Blob>(`${type == 'courseware' ? this.getCoursewareUrl :
      this.getFileResourceUrl}/${id}`,
      { responseType: 'blob' as 'json' })
  }

  addResource(id, name, url): Observable<Resource> {
    return this.http.post<Resource>(`${this.addResourceUrl}/${this.activeNodeMap.get(id)}`,
      { name, url })
      .pipe(map(resource => {
        if (this.activeNodeId == id) {
          this._activeAttachments.resources.push(resource);
          this.activeAttachments.next(this._activeAttachments);
        }
        return resource
      }))
  }

  getResource(id): Observable<any> {
    return this.http.get(`${this.getUrlResourceUrl}/${id}`)
  }

  updateResource(id, name, url): Observable<Resource> {
    return this.http.post<Resource>(`${this.updateResourceUrl}/${id}`, {name, url})
      .pipe(map(resource => {
        this.replace(this._activeAttachments.resources, resource);
        this.activeAttachments.next(this._activeAttachments);
        return resource
      }))
  }

  private replace(collection: any[], item) {
    let index;
    if ((index = collection.findIndex(v => v.id == item.id)) != -1)
      collection[index] = item
  }
}
