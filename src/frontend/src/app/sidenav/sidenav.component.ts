import {NestedTreeControl} from '@angular/cdk/tree';
import {Component, Injectable} from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {BehaviorSubject, of as observableOf} from 'rxjs';
import {NumberSymbol} from "@angular/common";

/**
 * Json node data with nested structure. Each node has a filename and a value or a list of children
 */
export class Node {
  children: Node[];
  id: any;
  name: string;
}

/**
 * The Json tree data in string. The data could be parsed into Json object
 */
const TREE_DATA = `
  [
    {
        "id": 122,
        "name": "apue",
        "maps": [
            {
                "id": 168,
                "name": "map3",
                "rootNode": null
            },
            {
                "id": 169,
                "name": "map2",
                "rootNode": null
            },
            {
                "id": 167,
                "name": "map1",
                "rootNode": null
            }
        ]
    },
    {
        "id": 120,
        "name": "ooad",
        "maps": [
            {
                "id": 176,
                "name": "mp4",
                "rootNode": null
            }
        ]
    },
    {
        "id": 125,
        "name": "ics",
        "maps": []
    },
    {
        "id": 171,
        "name": "sss",
        "maps": []
    }
]
  `;

/**
 * File database, it can build a tree structured Json object from string.
 * Each node in Json object represents a file or a directory. For a file, it has filename and type.
 * For a directory, it has filename and children (a list of files or directories).
 * The input will be a json object string, and the output is a list of `FileNode` with nested
 * structure.
 */
@Injectable()
export class FileDatabase {
  dataChange: BehaviorSubject<Node[]> = new BehaviorSubject<Node[]>([]);

  get data(): Node[] { return this.dataChange.value; }

  constructor() {
    this.initialize();
  }

  initialize() {
    // Parse the string to json object.
    const dataObject = JSON.parse(TREE_DATA);

    // Build the tree nodes from Json object. The result is a list of `FileNode` with nested
    //     file node as children.
    const data = this.buildFileTree(dataObject, 0);

    // Notify the change.
    this.dataChange.next(data);
  }

  /**
   * Build the file structure tree. The `value` is the Json object, or a sub-tree of a Json object.
   * The return value is the list of `FileNode`.
   */
  buildFileTree(value: any, level: number): Node[] {
    const dataObject = JSON.parse(TREE_DATA);
    let data: any[] = [];
    for (let i in dataObject) {
      let courceData = dataObject[i];
      let courseNode = new Node();
      courseNode.id = courceData["id"];
      courseNode.name = courceData["name"];
      courseNode.children = [];
      for (let j in courceData["maps"]) {
        let mapData = courceData["maps"][j];
        let mapNode = new Node();
        mapNode.id = mapData["id"];
        mapNode.name = mapData["name"];
        courseNode.children.push(mapNode);
      }
      data.push(courseNode)
    }
    return data;
  }
}

/**
 * @title Tree with nested nodes
 */
@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
  providers:[FileDatabase]
})
export class SidenavComponent {
  nestedTreeControl: NestedTreeControl<Node>;
  nestedDataSource: MatTreeNestedDataSource<Node>;

  a = 1;

  constructor(database: FileDatabase) {
    this.nestedTreeControl = new NestedTreeControl<Node>(this._getChildren);
    this.nestedDataSource = new MatTreeNestedDataSource();

    database.dataChange.subscribe(data => this.nestedDataSource.data = data);


  }

  private _getChildren = (node: Node) => { return observableOf(node.children); };
  hasNestedChild = (_: number, nodeData: Node) => {return nodeData.children; };

  delete(deleteId) {
    let data = this.nestedDataSource.data;
    for (let i in data)
      for (let j in data[i].children) {
        console.log(i, "-----", j,"-----",data[i].children[j]);
        if (data[i].children[j].id == deleteId) {
          data[i].children.splice(Number(j), 1);
        }
      }
    this.nestedDataSource.data = data;
    console.log(this.nestedDataSource.data)
  }

  items = [1,2,3,4];

  test233() {

//    this.nestedDataSource.data.splice(0,1);
//  this.nestedDataSource.data[0].children.splice(0,1);
   // console.log(this.nestedDataSource.data)

  }
}

