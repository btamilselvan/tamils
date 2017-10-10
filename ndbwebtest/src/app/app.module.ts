import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AppComponent} from './app.component';
import { CountryListComponent } from './country/country-list.component';
import {CountryModule} from './country/country.module';
import { CountryService } from './country/country.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import { AdminComponent } from './admin/admin/admin.component';
import { AdminService } from './admin/admin/admin.service';
import { CountryDropDownComponent } from './country/country-drop-down.component';
import { AddStateComponent } from './state/add-state.component';
import { HandsOnComponent } from './handson/hands-on/hands-on.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    AddStateComponent,
    CountryDropDownComponent,
    HandsOnComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, HttpModule, CountryModule, AppRoutingModule, FormsModule
  ],
  providers: [AdminService],
  bootstrap: [AppComponent]
})
export class AppModule {}