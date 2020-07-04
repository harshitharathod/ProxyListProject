import {Component, OnInit, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {ProxyDataService} from 'src/app/proxy-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(public dialogRef: MatDialogRef<DeleteComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public dataService: ProxyDataService, private _snackBar: MatSnackBar) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmDelete(): void {
    this.dataService.deleteProxy(this.data).subscribe(val => {
      console.log(val);
      this.dataService.refreshData.next(true);
      this._snackBar.open(" Deleted Successfully",":-)",{
        duration: 3000,
      });    });
  }

}
