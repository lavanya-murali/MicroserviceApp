import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Data } from './app.data';

@Injectable()
export class AppService {
private headers = new Headers({'Content-Type': 'application/json'});
private restUrl = 'http://localhost:2222/swap';  // URL to web api
  constructor(private http: Http) { }
  getSwappedData(input: Data): Promise<Data> {
        return this.http
          .post(this.restUrl, JSON.stringify({data: input}), {headers: this.headers})
          .toPromise()
          .then(res => res.json().data as Data)
          .catch(this.handleError);
      }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}