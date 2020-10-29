import {BaseService} from '../base.service';
import {Country} from '../country/country';
import {State} from './state';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/map';

@Injectable()
export class StateService extends BaseService {

  private statesURL = this.ndbUrl + 'state/';

  constructor(private http: HttpClient) {
    super();
  }

  getStates(countryCode: string): Observable<State[]> {
    return this.http.get(this.statesURL + 'country/' + countryCode).map(
      data => data as State[],
      error => super.handleError);
  }

  createState(state: State): Observable<State> {
    return this.http.post(this.statesURL + 'add', JSON.stringify(state), {headers: this.headers}).map(
      data => data as State,
      error => {
        super.handleError(error);
      });
  }
}
