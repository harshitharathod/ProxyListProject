import {Injectable} from '@angular/core';
import {Proxies} from './proxies';
import {Proxylist} from './proxylist';
import {Observable, BehaviorSubject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProxyDataService {
  dataChange: BehaviorSubject<Proxies[]> = new BehaviorSubject<Proxies[]>([]);
  private baseUrl = '/proxies/api';
  dialogData: any;
  refreshData: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


  constructor(private httpclient: HttpClient) {
  }
  

  getProxies(): Observable<Proxies[]> {
    return this.httpclient.get<Proxies[]>(`${this.baseUrl + '/getProxies'}`);
  }

  getProxy(id: number): Observable<Proxies[]> {
    return this.httpclient.get<Proxies[]>(`${this.baseUrl + '/getProxy/id'}`);
  }

  createProxy(proxy: Proxies) {
    return this.httpclient.post(`${this.baseUrl + '/postProxy'}`, proxy);
  }

  updateProxy(proxy: Proxies) {
    return this.httpclient.put(`${this.baseUrl + '/putProxy'}`, proxy);
  }

  deleteProxy(proxy: Proxies): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders(), body: proxy
    };
    return this.httpclient.delete<any>(`${this.baseUrl + '/deleteProxy'}`, httpOptions);
  }



  getProxyLists(): Observable<Proxylist[]> {
    return this.httpclient.get<Proxylist[]>(`${this.baseUrl + '/getProxyLists'}
    
`);

  }



  getDialogData() {
    return this.dialogData;
  }
}
