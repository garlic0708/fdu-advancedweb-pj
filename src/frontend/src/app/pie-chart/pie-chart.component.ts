import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as Highcharts from 'highcharts';
import {chart} from 'highcharts';
import {mockData} from "../../assets/mock-data";


@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  questionResult = mockData['GET /api/questionResult/:qid'];
  result = this.questionResult.result;

  @ViewChild('chartTarget') chartTarget: ElementRef;

  chart: Highcharts.ChartObject;

  title = 'bv';

  ngOnInit(): void {
  }

  ngAfterContentInit() {
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
    return Object.keys(this.questionResult.result).map(item => {
      return {
        name: item,
        y: Number(this.questionResult.result[item]),
      }
    });
  }
}


