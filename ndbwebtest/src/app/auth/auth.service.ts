import {BaseService} from '../base.service';
import {HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable()
export class AuthService extends BaseService {
  private failedRequests: Array<HttpRequest<any>> = [];
  loggedIn = false;
  jwtHelper = new JwtHelperService();

  getToken(): string {
    console.log('inside auth service - ndbtoken->' + localStorage.getItem('ndbtoken'));
    if (this.isAuthenticated()) {
      console.log('token not found. forward to login page..');
    }
    //    console.log('toekn => '+this.jwtHelper.decodeToken(localStorage.getItem('ndbtoken')));
    return localStorage.getItem('ndbtoken');
  }
  setToken(token) {
    console.log('inside auth service - settotkem->');
    localStorage.setItem('ndbtoken', 'Bearer ' + token);
  }
  isAuthenticated(): boolean {
    return this.jwtHelper.isTokenExpired(localStorage.getItem('ndbtoken'));
  }
  collectFailedRequests(failedHttpRequest: HttpRequest<any>) {
    this.failedRequests = [];
    this.failedRequests.push(failedHttpRequest);
  }
  logout() {
    this.removeToken();
    this.loggedIn = false;
  }
  removeToken() {
    localStorage.removeItem('ndbtoken');
  }
}
