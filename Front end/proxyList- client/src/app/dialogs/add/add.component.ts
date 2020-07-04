import {Component, OnInit, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {Proxies} from 'src/app/proxies';
import {ProxyDataService} from 'src/app/proxy-data.service';
import {FormControl, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Proxies,
              public proxyDataService: ProxyDataService, public dialog: MatDialog, private _snackBar: MatSnackBar) {
  }

  formControl = new FormControl('', [
    Validators.required
  ]);
  ngOnInit(): void {
  }


  submit() {
// emppty stuff
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public confirmAdd(): void {
    this.proxyDataService.createProxy(this.data).subscribe(val => {
      console.log(val);
      this.proxyDataService.refreshData.next(true);
      this._snackBar.open(" Added Successfully",":-)",{
        duration: 3000,
      });
     
    });
  }


}


