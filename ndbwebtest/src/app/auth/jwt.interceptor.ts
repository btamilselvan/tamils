import {AdminService} from '../admin/admin.service';
import {Injectable, Injector} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/do';
@Injectable()
export class JWTInterceptor implements HttpInterceptor {
  constructor(private injector: Injector, private router: Router) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('response service ');
    let adminService = this.injector.get(AdminService);
    return next.handle(req).do((event: HttpEvent<any>) => {
      if (event instanceof HttpResponse) {
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          console.log('error 401 ');
          adminService.collectFailedRequests(req);
          this.router.navigate(['/login']);
        }
      }
    });
  }
}
