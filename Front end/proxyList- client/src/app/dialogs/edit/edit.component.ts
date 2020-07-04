import {Component, OnInit, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {ProxyDataService} from 'src/app/proxy-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public proxyDataService: ProxyDataService,private _snackBar: MatSnackBar) {
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

  stopEdit(): void {
    this.proxyDataService.updateProxy(this.data).subscribe(val => {
      console.log(val);
      this.proxyDataService.refreshData.next(true);
      this._snackBar.open("Updated Successfully",":-)",{
        duration: 3000,
      });    
    });
  }

}
