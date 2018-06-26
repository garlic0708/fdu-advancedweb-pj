import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlattener, MatTreeFlatDataSource } from '@angular/material/tree';
import { of as ofObservable, Observable, BehaviorSubject } from 'rxjs';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from "@angular/material";
import { CourseService } from "./course.service";
import remove = require('lodash/remove')
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from "@angular/router";
import { CurrentUserService } from "../../current-user.service";


/**
 * Node for to-do item
 */
export class Node {
  children: Node[];
  name: string;
  id: number;
  selected: boolean = false;
}

/** Flat to-do item node with expandable and level information */
export class FlatNode {
  name: string;
  id: number;
  level: number;
  expandable: boolean;
  hasChildren: boolean;
  selected: boolean;
}

/**
 * Course list database, it can build a tree structured Json object.
 * Each node in Json object represents a course or a .
 * If a node is a category, it has children items and new items can be added under the category.
 */
@Injectable()
export class CourseListDatabase {
  dataChange: BehaviorSubject<Node[]> = new BehaviorSubject<Node[]>([]);
  selected: Node | null = null;
  private resolveInitialize: Function;
  private initialized: Promise<any> = new Promise(resolve => this.resolveInitialize = resolve);

  private nextCount = (function () {
    let counter = -1;
    return function () {
      return counter--;
    }
  })();

  get data(): Node[] {
    return this.dataChange.value;
  }

  constructor() {
  }

  initialize(courses) {
    // Build the tree nodes from Json object. The result is a list of `TodoItemNode` with nested
    //     file node as children,
    // And notify the change.
    this.dataChange.next(CourseListDatabase.buildFileTree(courses, 0));
    this.resolveInitialize('ok')
  }

  /**
   * Build the file structure tree. The `value` is the Json object, or a sub-tree of a Json object.
   * The return value is the list of `TodoItemNode`.
   */
  static buildFileTree(value: any, level: number) {
    const dataObject = value;
    let data: any[] = [];
    let rootNode = new Node();
    rootNode.name = "所有课程";
    rootNode.children = [];
    for (let i in dataObject) {
      let courseData = dataObject[i];
      let courseNode = new Node();
      courseNode.name = courseData["name"];
      courseNode.id = courseData["id"];
      courseNode.children = [];
      for (let j in courseData["maps"]) {
        let mapData = courseData["maps"][j];
        let mapNode = new Node();
        mapNode.name = mapData["name"];
        mapNode.id = mapData["id"];
        courseNode.children.push(mapNode);
      }
      rootNode.children.push(courseNode);
    }
    data.push(rootNode);
    return data;
  }

  /** Add an item to to-do list */
  insert(parent: Node, name: string, level: number) {
    console.log(level);
    const child = <Node>{ name: name, id: this.nextCount() };
    if (level == 0) child.children = [];
    if (parent.children) {
      parent.children.push(child);
      this.dataChange.next(this.data);
    }
  }

  update(node: Node, name: string, id: number) {
    node.name = name;
    node.id = id;
    this.dataChange.next(this.data);
  }

  updateFull(node: Node, data) {
    node.name = data.name;
    node.id = data.id;
    node.children = data.maps.map(map => {
      return { id: map.id, name: map.name }
    });
    this.dataChange.next(this.data)
  }

  delete(node: Node, level: number) {
    if (level == 1)
      remove(this.data[0].children, n => n.id == node.id);
    else
      for (let course of this.data[0].children) {
        if (remove(course.children, n => n.id == node.id).length) break
      }
    this.dataChange.next(this.data);
  }

  markSelected(id: number) {
    console.log('waiting...');
    this.initialized.then(() => {
      console.log('complete!', id);
      if (this.selected) {
        if (this.selected.id == id) return;
        this.selected.selected = false;
      }
      if (!id) this.selected = null;
      else {
        let node;
        for (let course of this.data[0].children) {
          if (node = course.children.find(n => n.id == id)) {
            node.selected = true;
            this.selected = node;
            break
          }
        }
        this.dataChange.next(this.data);
      }
    })
  }
}


@Component({
  selector: 'app-sidenav-dialog',
  templateUrl: 'sidenav.dialog.component.html',
})
export class DeleteDialogComponent {

  deleting: boolean = false;

  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Observable<any>) {
  }

  onNoClick(): void {
    this.dialogRef.close(0);
  }

  onClick() {
    this.deleting = true;
    this.data.subscribe(() => {
      this.deleting = false;
      this.dialogRef.close(1);
    });
  }
}

/**  Copyright 2018 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
  providers: [CourseListDatabase, DeleteDialogComponent]
})
export class SidenavComponent implements OnInit {
  opened = true;

  /** Map from flat node to nested node. This helps us finding the nested node to be modified */
  flatNodeMap: Map<FlatNode, Node> = new Map<FlatNode, Node>();

  /** Map from nested node to flattened node. This helps us to keep the same object for selection */
  nestedNodeMap: Map<Node, FlatNode> = new Map<Node, FlatNode>();

  treeControl: FlatTreeControl<FlatNode>;

  treeFlattener: MatTreeFlattener<Node, FlatNode>;

  dataSource: MatTreeFlatDataSource<Node, FlatNode>;

  creating = 0;
  parentNodeId: number;

  options = [];

  private initMindmapRoute: Function;
  private mindmapRoute: Promise<ActivatedRoute> =
    new Promise<ActivatedRoute>(resolve => this.initMindmapRoute = resolve);

  constructor(private database: CourseListDatabase, public dialog: MatDialog,
              private courseService: CourseService,
              private route: ActivatedRoute,
              private router: Router,
              private currentUser: CurrentUserService) {
    this.treeFlattener = new MatTreeFlattener(this.transformer, this.getLevel,
      this.isExpandable, this.getChildren);
    this.treeControl = new FlatTreeControl<FlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    database.dataChange.subscribe(data => {
      console.log(data);
      this.dataSource.data = data;
    });
  }

  ngOnInit() {
    this.courseService.getCourses().subscribe(courses => {
      this.database.initialize(courses);
    });

    // this.route.firstChild could be at first not 'mindmap'
    // So we make a promise here which resolves until we find the mindmap child route
    if (this.route.firstChild && this.route.firstChild.snapshot.url[0].path == 'mindmap')
      this.initMindmapRoute(this.route.firstChild);
    else {
      const subscription = this.router.events.subscribe(e => {
        if (e instanceof NavigationEnd && this.router.isActive('/mindmap', false)) {
          console.log(this.route.firstChild);
          this.initMindmapRoute(this.route.firstChild);
          subscription.unsubscribe()
        }
      });
    }

    this.mindmapRoute.then(route => route.paramMap.subscribe(params => {
      const id = params.get('mapId');
      this.database.markSelected(id ? +id : null)
    }))
  }

  getLevel = (node: FlatNode) => {
    return node.level;
  };

  isExpandable = (node: FlatNode) => {
    return node.expandable;
  };

  getChildren = (node: Node): Observable<Node[]> => {
    return ofObservable(node.children);
  };

  hasChild = (_: number, _nodeData: FlatNode) => {
    return _nodeData.expandable;
  };

  hasNoContent = (_: number, _nodeData: FlatNode) => {
    return _nodeData.name === '';
  };

  /**
   * Transformer to convert nested node to flat node. Record the nodes in maps for later use.
   */
  transformer = (node: Node, level: number) => {
    let flatNode = this.nestedNodeMap.has(node) && this.nestedNodeMap.get(node)!.name === node.name
      ? this.nestedNodeMap.get(node)!
      : new FlatNode();
    flatNode.name = node.name;
    flatNode.id = node.id;
    flatNode.level = level;
    flatNode.expandable = !!node.children;
    flatNode.hasChildren = !!(node.children && node.children.length);
    flatNode.selected = node.selected;
    this.flatNodeMap.set(flatNode, node);
    this.nestedNodeMap.set(node, flatNode);
    return flatNode;
  };

  /** Select the category so we can insert the new item. */
  addNode(node: FlatNode) {
    this.creating = 1;

    const parentNode = this.flatNodeMap.get(node);
    this.database.insert(parentNode!, '', node.level);
    this.treeControl.expand(node);
    if (!this.isTeacher())
      this.options = [];
    this.parentNodeId = node.id;
  }

  /** Save the node to database */
  saveNode(node: FlatNode, value: string) {
    if (value != "") {
      this.creating = 2;
      const next = ({ id, name }) => {
        this.creating = 0;
        const nestedNode = this.flatNodeMap.get(node);
        this.database.update(nestedNode!, name, id);
      };
      if (node.level == 1)
        this.courseService.addCourse(value).subscribe(next);
      else
        this.courseService.addMindmap(this.parentNodeId, value)
          .subscribe(next)
    } else this.cancelAddNode(node)
  }

  selectCourse(node: FlatNode, id) {
    console.log(node, id);
    this.creating = 2;
    this.courseService.selectCourse(id).subscribe(data => {
      this.creating = 0;
      const nestedNode = this.flatNodeMap.get(node);
      this.database.updateFull(nestedNode, data)
    })
  }

  cancelAddNode(node: FlatNode) {
    this.creating = 0;
    this.deleteNode(node)
  }

  deleteNode(node: FlatNode) {
    const nestedNode = this.flatNodeMap.get(node);
    this.database.delete(nestedNode!, node.level);
  }

  openDialog(node: FlatNode, type: string): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: type == 'course' ?
        (this.isTeacher() ? this.courseService.deleteCourse(node.id) : this.courseService.deselectCourse(node.id))
        : this.courseService.deleteMindMap(node.id)
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result == 1) this.deleteNode(node);
    });
  }

  openMap(id) {
    console.log('id', id);
    // this.router.navigate(['/mindmap', id.toString()], {relativeTo: this.route})
    this.router.navigateByUrl(`/app/mindmap/${id}`);
    // this.database.markSelected(id);
  }

  canAdd(node: FlatNode) {
    return this.currentUser.currentUserRole == 'TEACHER' ? true
      : node.level == 0
  }

  isTeacher() {
    return this.currentUser.currentUserRole == 'TEACHER'
  }

  inputChanged(value) {
    console.log(value);
    this.courseService.queryCourse(value)
      .subscribe(v => {
        console.log(v);
        this.options = v;
      })
  }
}
