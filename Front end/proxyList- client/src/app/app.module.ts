import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule, MatTableDataSource} from '@angular/material/table';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { ProxyDataTableComponent } from './proxy-data-table/proxy-data-table.component';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input'
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { AddComponent } from './dialogs/add/add.component';
import { EditComponent } from './dialogs/edit/edit.component';
import { DeleteComponent } from './dialogs/delete/delete.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { IntroPageComponent } from './intro-page/intro-page.component';
import {RouterModule,Routes} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatRadioModule} from '@angular/material/radio';
import {MatSnackBarModule} from '@angular/material/snack-bar';




const routes:Routes=[
  {path: '' , component : IntroPageComponent},
  {path: "proxyList", component: ProxyDataTableComponent },
]



@NgModule({
  declarations: [
    AppComponent,
    ProxyDataTableComponent,
    AddComponent,
    EditComponent,
    DeleteComponent,
    IntroPageComponent
  ],
  imports: [
    BrowserModule,
    MatSelectModule,
    FormsModule,
   MatTableModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    RouterModule.forRoot(routes),
    MatSortModule,
    HttpClientModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatToolbarModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule,
    ReactiveFormsModule,
    MatSnackBarModule
 
  ],
  entryComponents: [
    AddComponent,
    EditComponent,
    DeleteComponent
  ],
  providers: [
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: [] },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
