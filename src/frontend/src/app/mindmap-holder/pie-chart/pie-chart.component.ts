import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import * as Highcharts from 'highcharts';
import { chart } from 'highcharts';
import { NodeService } from "../node.service";


@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit, AfterViewInit {

  // questionResult = mockData['GET /api/questionResult/:qid'];
  result;
  loading: boolean = true;

  private componentInitResolve: Function;
  private componentInitPromise = new Promise(resolve => this.componentInitResolve = resolve);

  @ViewChild('chartTarget') chartTarget;
  private _qid: number = null;
  @Input() set qid(newId) {
    if (!this._qid && newId) {
      this._qid = newId;
      this.nodeService.getMultipleChoiceAnswers(newId)
        .subscribe(v => {
          this.result = v;
          this.loading = false;
          this.componentInitPromise.then(() => {
            this.initContent();
          })
        })
    }
  }

  chart: Highcharts.ChartObject;

  title = 'bv';

  constructor(private nodeService: NodeService) {}

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    console.log(this.chartTarget);
    this.componentInitResolve()
  }

  initContent() {
    const options: Highcharts.Options = {
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
      },
      title: {
        text: '选项选择人数分布图'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y}人</b>'
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            style: {
              color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
            }
          }
        }
      },
      series: [{
        name: '选项',
        colorByPoint: true,
        data: this.resultKeys,
      }]
    };

    this.chart = chart(this.chartTarget.nativeElement, options);
  }

  ngOnDestroy() {
    this.chart = null;
  }

  get resultKeys() {
    return Object.keys(this.result).map(item => {
      return {
        name: item,
        y: Number(this.result[item]),
      }
    });
  }
}


