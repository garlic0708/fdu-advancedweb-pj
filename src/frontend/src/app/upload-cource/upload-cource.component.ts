import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';

@Component({
  selector: 'app-upload-cource',
  templateUrl: './upload-cource.component.html',
  styleUrls: ['./upload-cource.component.css']
})
export class UploadCourceComponent implements OnInit {

  public uploader:FileUploader = new FileUploader(
    {url: "https://evening-anchorage-3159.herokuapp.com/api/", method:"POST", isHTML5:true});  //options
  /*
| 参数名 | 参数类型 | 是否是可选值 | 参数说明 |
 |:---- --:|:--------:|:-------------:|:--------:|
 | allowedMimeType | Array<string> | 可选值 | |
 | allowedFileType | Array<string> | 可选值 | 允许上传的文件类型 |
 | autoUpload | boolean | 可选值 | 是否自动上传 |
 | isHTML5 | boolean | 可选值 | 是否是HTML5 |
 | filters | Array<FilterFunction> | 可选值 | |
 | headers | Array<Headers> | 可选值 | 上传文件的请求头参数 |
 | method | string | 可选值 | 上传文件的方式 |
 | authToken | string | 可选值 | auth验证的token |
 | maxFileSize | number | 可选值 | 最大可上传文件的大小 |
 | queueLimit | number | 可选值 | |
 | removeAfterUpload | boolean | 可选值 | 是否在上传完成后从队列中移除 |
 | url | string | 可选值 | 上传地址 |
 | disableMultipart | boolean | 可选值 | |
 | itemAlias | string | 可选值 | 文件标记／别名 |
 | authTokenHeader | string | 可选值 | auth验证token的请求头 |
*/

  public hasBaseDropZoneOver:boolean = false;

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }


  ngOnInit() {
  }

}
