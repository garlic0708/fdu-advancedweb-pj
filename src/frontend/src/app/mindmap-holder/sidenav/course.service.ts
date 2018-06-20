import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private courseUrl = '/api/course/get';
  private addCourseUrl = '/api/course/add';
  private addMindMapUrl = '/api/mindmap/add';
  private deleteCourseUrl = '/api/course/delete';
  private deleteMindMapUrl = '/api/mindmap/delete';
  private selectCourseUrl = '/api/course/select';
  private deselectCourseUrl = '/api/course/deselect';
  private queryCourseUrl = '/api/course/query';

  constructor(private http: HttpClient) {
  }

  getCourses(): Observable<any> {
    return this.http.get(`${this.courseUrl}`,);
  }

  addCourse(name): Observable<any> {
    return this.http.post(this.addCourseUrl, { name })
  }

  addMindmap(courseId, name): Observable<any> {
    return this.http.post(`${this.addMindMapUrl}/${courseId}`, { name })
  }

  deleteCourse(id): Observable<any> {
    return this.http.delete(`${this.deleteCourseUrl}/${id}`)
  }

  deleteMindMap(id): Observable<any> {
    return this.http.delete(`${this.deleteMindMapUrl}/${id}`)
  }

  selectCourse(id): Observable<any> {
    return this.http.post(`${this.selectCourseUrl}/${id}`, null)
  }

  deselectCourse(id): Observable<any> {
    return this.http.post(`${this.deselectCourseUrl}/${id}`, null)
  }

  queryCourse(name): Observable<any[]> {
    return this.http.get<any[]>(`${this.queryCourseUrl}/${name}`)
  }
}
