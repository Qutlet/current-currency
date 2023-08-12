import { Component } from '@angular/core';
import { ListUsage } from './list-usage'
import { ListUsageService } from './list-usage.service'

@Component({
  selector: 'app-list-usage',
  templateUrl: './list-usage.component.html',
  styleUrls: ['./list-usage.component.css']
})
export class ListUsageComponent {

  response: ListUsage[] = [];
  pageSize: number = 10;
  currentPage: number = 1;


  constructor(
    private listUsageService: ListUsageService
  ) {
   this.loadData();
  }

  refreshData(): void {
    this.loadData();
  }

  private loadData(): void {
    this.listUsageService.getUsage()
      .subscribe((response: ListUsage[]) => {
        this.response = response;
      });
  }

}
