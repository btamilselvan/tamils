import {AdminComponent} from './admin/admin.component';
import {AuthGuardService} from './auth/auth-guard.service';
import { SocialAuthComponent } from './auth/social-auth/social-auth.component';
import {AddCityComponent} from './city/add-city.component';
import {CountryListComponent} from './country/country-list.component';
import {AddDistrictComponent} from './district/add-district.component';
import {HandsOnComponent} from './handson/hands-on/hands-on.component';
import { HomeComponent } from './home/home.component';
import {AddPersonComponent} from './person/add-person.component';
import {PersonDetailComponent} from './person/person-detail.component';
import {PersonSearchComponent} from './person/person-search.component';
import {AddStateComponent} from './state/add-state.component';
import {LoginComponent} from './user/login.component';
import {Routes, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import { CallbackComponent } from './auth/twitter/callback/callback.component';

const appRoutes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'social-login', component: SocialAuthComponent
  },
  {
    path: 'twitter/callback', component: CallbackComponent
  },
  {
    path: '', component: HomeComponent
  },
  {
    path: 'search/:param', component: PersonSearchComponent
  },
  {
    path: 'person/:id', component: PersonDetailComponent
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuardService],
    children: [{
      path: 'add-state',
      component: AddStateComponent
    },
    {
      path: 'add-city',
      component: AddCityComponent
    },
    {
      path: 'add-district',
      component: AddDistrictComponent
    },
    {
      path: 'add-person',
      component: AddPersonComponent
    }
    ]
  },
  {
    path: 'hands-on',
    component: HandsOnComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
