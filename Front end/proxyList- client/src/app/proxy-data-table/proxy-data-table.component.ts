import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {ProxyDataService} from '../proxy-data.service';
import {Proxies} from '../proxies';
import {Observable} from 'rxjs';
import {Proxylist} from '../proxylist';
import {MatTableDataSource, MatTable} from '@angular/material/table';
import {CdkTableModule} from '@angular/cdk/table';



import {MatDialog} from '@angular/material/dialog';
import {AddComponent} from '../dialogs/add/add.component';
import {EditComponent} from '../dialogs/edit/edit.component';
import {DeleteComponent} from '../dialogs/delete/delete.component';
import {MatPaginator} from '@angular/material/paginator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-proxy-data-table',
  templateUrl: './proxy-data-table.component.html',
  styleUrls: ['./proxy-data-table.component.css']
})
export class ProxyDataTableComponent implements OnInit {
  selectedProxy: number;
  selectedValue: string;
  selectedProxyList: Proxies[];
  ipaddress: string;
  port: number;
  index: number;
  exampleDatabase: ProxyDataService | null;


  proxyDataSource: Proxies[];
  displayedColumns = ['IP Address', 'port', 'test_result','anonymity','first found Address', 'last found Address', 'action'];
  dataSource: { ipaddress: any; port: any; }[];
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild('filter', {static: true}) filter: ElementRef;

  constructor(private proxyDataService: ProxyDataService, public dialog: MatDialog, private router:Router) {
    this.proxyDataService.getProxies().subscribe(value => {
        this.proxyDataSource = value;
      }
    );
  }

  ngOnInit(): void {
    this.proxyDataService.refreshData.subscribe(
      value => {
        if (value) {
          this.proxyDataService.getProxies().subscribe(proxies => {
              this.proxyDataSource = proxies;
              this.onProxySelection();
            }
          );
        }
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    // deletthis.dataSource = new MatTableDataSource(this.selectedProxyList);
    this.selectedProxyList = this.proxyDataSource.filter(proxy => proxy.id == this.selectedProxy && proxy.ipaddress.includes(filterValue));
    // this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  onProxySelection() {
    this.selectedValue='none';
    console.log(this.selectedProxy);
    this.selectedProxyList = this.proxyDataSource.filter(proxy => proxy.id == this.selectedProxy);
    console.log(this.selectedProxyList);
  }

  onDisplayingProxySelection() {
    if (this.selectedValue === 'UP') {
      this.selectedProxyList = this.selectedProxyList.filter(proxy => proxy.test_result == this.selectedValue);
    }else{
      this.selectedProxyList = this.proxyDataSource.filter(proxy => proxy.id == this.selectedProxy);
    }
  }

  addNew() {
    const dialogRef = this.dialog.open(AddComponent, {
      data: {proxies: Proxies}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.exampleDatabase.dataChange.value.push(this.proxyDataService.getDialogData());
        this.refreshTable();
      }
    });
  }


  startEdit(proxy: any) {
    const dialogRef = this.dialog.open(EditComponent, {
      data: proxy
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // Based on ur requirement u can right after close logic here
      /*  this.proxyDataService.updateProxy(this.editData).subscribe(val => {
          // When using an edit things are little different, firstly we find record inside DataService by id
          const foundIndex = this.proxyDataService.dataChange.value.findIndex(x => x.ipaddress === this.ipaddress);
          console.log(foundIndex);
          // Then you update that record using data from dialogData (values you enetered)
          this.exampleDatabase.dataChange.value[foundIndex] = this.proxyDataService.getDialogData();
          // And lastly refresh table
          this.refreshTable();
        });*/

      }
    });
  }

  refreshTable() {
    this.paginator._changePageSize(this.paginator.pageSize);
  }

  deleteItem(proxy: any) {
    console.log(proxy);
    const dialogRef = this.dialog.open(DeleteComponent, {
      data: proxy
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // Based on ur requirement u can right after close logic here
        /* const foundIndex = this.proxyDataService.dataChange.value.findIndex(x => x.ipaddress === this.ipaddress);
         // for delete we use splice in order to remove single object from DataService
         this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
         this.exampleDatabase.getProxies();*/
      }
    });

  }


}

export class ProxyDataSource extends DataSource<any> {
  filter: string;

  constructor(private proxyDataService: ProxyDataService) {
    super();
  }

  connect(): Observable<Proxies[]> {
    return this.proxyDataService.getProxies();
  }

  disconnect() {
  }

  


}
