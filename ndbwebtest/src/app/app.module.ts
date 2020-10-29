import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AppComponent} from './app.component';
import {CountryListComponent} from './country/country-list.component';
import {CountryModule} from './country/country.module';
import {CountryService} from './country/country.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AdminComponent} from './admin/admin.component';
import {AdminService} from './admin/admin.service';
import {AuthGuardService} from './auth/auth-guard.service';
import {AuthService} from './auth/auth.service';
import {JWTInterceptor} from './auth/jwt.interceptor';
import {TokenInterceptor} from './auth/token.interceptor';
import {AddCityComponent} from './city/add-city.component';
import {CityService} from './city/city.service';
import {CountryDropDownComponent} from './country/country-drop-down.component';
import {AddDistrictComponent} from './district/add-district.component';
import {DistrictService} from './district/district.service';
import {AddStateComponent} from './state/add-state.component';
import {HandsOnComponent} from './handson/hands-on/hands-on.component';
import {HomeComponent} from './home/home.component';
import {PersonDetailComponent} from './person/person-detail.component';
import {PersonSearchComponent} from './person/person-search.component';
import {PersonService} from './person/person.service';
import {SharedModule} from './shared.module';
import {StateService} from './state/state.service';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthServiceConfig, GoogleLoginProvider, FacebookLoginProvider, SocialLoginModule} from 'angularx-social-login';
import {SocialAuthComponent} from './auth/social-auth/social-auth.component';
import { SocialAuthService } from './auth/social-auth/social-auth.service';
import { CallbackComponent } from './auth/twitter/callback/callback.component';

let config = new AuthServiceConfig([
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('329977280847388')
  }
]);

export function provideConfig() {
  return config;
}

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    AddStateComponent,
    CountryDropDownComponent,
    HandsOnComponent,
    AddCityComponent,
    AddDistrictComponent,
    PersonSearchComponent,
    PersonDetailComponent,
    HomeComponent,
    SocialAuthComponent,
    CallbackComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, HttpClientModule, CountryModule, AppRoutingModule, FormsModule, SharedModule, SocialLoginModule
  ],
  providers: [
    {provide: AdminService, useClass: AdminService},
    {provide: StateService, useClass: StateService},
    {provide: DistrictService, useClass: DistrictService},
    {provide: CityService, useClass: CityService},
    {provide: PersonService, useClass: PersonService},
    {provide: AuthService, useClass: AuthService},
    {provide: AuthGuardService, useClass: AuthGuardService},
    {provide: SocialAuthService, useClass: SocialAuthService},
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: JWTInterceptor, multi: true},
    {provide: AuthServiceConfig, useFactory: provideConfig}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
