import {Component, Inject, Injectable} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlattener, MatTreeFlatDataSource} from '@angular/material/tree';
import {of as ofObservable, Observable, BehaviorSubject} from 'rxjs';
import {mockData} from "../../assets/mock-data";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";


/**
 * Node for to-do item
 */
export class Node {
  children: Node[];
  name: string;
  id: number;
}

/** Flat to-do item node with expandable and level information */
export class FlatNode {
  name: string;
  id: number;
  level: number;
  expandable: boolean;
}

/**
 * The Json object for to-do list data.
 */
const TREE_DATA = mockData["GET /api/course/get/:id"];

/**
 * Checklist database, it can build a tree structured Json object.
 * Each node in Json object represents a to-do item or a category.
 * If a node is a category, it has children items and new items can be added under the category.
 */
@Injectable()
export class ChecklistDatabase {
  dataChange: BehaviorSubject<Node[]> = new BehaviorSubject<Node[]>([]);

  get data(): Node[] { return this.dataChange.value; }

  constructor() {
    this.initialize();
  }

  initialize() {
    // Build the tree nodes from Json object. The result is a list of `TodoItemNode` with nested
    //     file node as children.
    const data = this.buildFileTree(TREE_DATA, 0);

    // Notify the change.
    this.dataChange.next(data);
  }

  /**
   * Build the file structure tree. The `value` is the Json object, or a sub-tree of a Json object.
   * The return value is the list of `TodoItemNode`.
   */
  buildFileTree(value: any, level: number) {
    const dataObject = value;
    let data: any[] = [];
    let rootNode = new Node();
    rootNode.name = "所有课程";
    rootNode.children = [];
    for (let i in dataObject) {
      let courceData = dataObject[i];
      let courseNode = new Node();
      courseNode.name = courceData["name"];
      courseNode.id = courceData["id"];
      courseNode.children = [];
      for (let j in courceData["maps"]) {
        let mapData = courceData["maps"][j];
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
    var child;
    if (level = 0) child = <Node>{name: name, children:[]};
    else child = <Node>{name: name};
    if (parent.children) {
      parent.children.push(child);
      this.dataChange.next(this.data);
    }
  }

  update(node: Node, name: string) {
    node.name = name;
    this.dataChange.next(this.data);
  }

  delete(node: Node) {
    for (let i in this.data) {
      var mapNode = this.data[i]["children"];
      for (let j in mapNode)
        if (mapNode[j]["id"] == node.id) {
            this.data[i]["children"].splice(Number(j), 1);
            this.dataChange.next(this.data);
            return
        }
    }
  }
}



@Component({
  selector: 'app-sidenav-dialog',
  templateUrl: 'sidenav.dialog.component.html',
})
export class DeleteDialogComponent {

  del = 1;

  constructor(
    public dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  onClick(): void {
    this.del = 1;
    this.dialogRef.close();
  }
}

/**  Copyright 2018 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
  providers: [ChecklistDatabase, DeleteDialogComponent]
})
export class SidenavComponent {
  opened = true;

  /** Map from flat node to nested node. This helps us finding the nested node to be modified */
  flatNodeMap: Map<FlatNode, Node> = new Map<FlatNode, Node>();

  /** Map from nested node to flattened node. This helps us to keep the same object for selection */
  nestedNodeMap: Map<Node, FlatNode> = new Map<Node, FlatNode>();

  treeControl: FlatTreeControl<FlatNode>;

  treeFlattener: MatTreeFlattener<Node, FlatNode>;

  dataSource: MatTreeFlatDataSource<Node, FlatNode>;

  creating = 0;

  constructor(private database: ChecklistDatabase, public dialog: MatDialog) {
    this.treeFlattener = new MatTreeFlattener(this.transformer, this.getLevel,
      this.isExpandable, this.getChildren);
    this.treeControl = new FlatTreeControl<FlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    database.dataChange.subscribe(data => {
      this.dataSource.data = data;
    });
  }

  getLevel = (node: FlatNode) => { return node.level; };

  isExpandable = (node: FlatNode) => { return node.expandable; };

  getChildren = (node: Node): Observable<Node[]> => {
    return ofObservable(node.children);
  }

  hasChild = (_: number, _nodeData: FlatNode) => { return _nodeData.expandable; };

  hasNoContent = (_: number, _nodeData: FlatNode) => { return _nodeData.name === ''; };

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
    this.flatNodeMap.set(flatNode, node);
    this.nestedNodeMap.set(node, flatNode);
    return flatNode;
  }

  /** Select the category so we can insert the new item. */
  addNode(node: FlatNode) {
    this.creating = 1;

    const parentNode = this.flatNodeMap.get(node);
    this.database.insert(parentNode!, '', node.level);
    this.treeControl.expand(node);
  }

  /** Save the node to database */
  saveNode(node: FlatNode, value: string) {
    if (value != "") {
      this.creating = 0;
      const nestedNode = this.flatNodeMap.get(node);
      this.database.update(nestedNode!, value);
    }
  }

  deleteNode(node: FlatNode) {
    const nestedNode = this.flatNodeMap.get(node);
    this.database.delete(nestedNode!);
  }

  openDialog(node: FlatNode): void {
    var del = 0;
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { del: del }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      del = result;
      if (del == 1) this.deleteNode(node);
    });
  }
}
