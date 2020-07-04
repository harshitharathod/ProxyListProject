import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProxyDataService } from '../proxy-data.service';

@Component({
  selector: 'app-intro-page',
  templateUrl: './intro-page.component.html',
  styleUrls: ['./intro-page.component.css']
})
export class IntroPageComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {

  }

  next(){
    this.router.navigate(['/proxyList'])
  }

}
