import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MenubarModule} from 'primeng/menubar';
import {BreadcrumbModule} from 'primeng/breadcrumb';

import {AppComponent} from './app.component';
import {PacketsComponent} from './packets/packets.component';
import {BreadCrumbComponent} from './bread-crumb/bread-crumb.component';
import {BreadCrumbService} from './bread-crumb/bread-crumb.service';
import {NavigationComponent} from './navigation/navigation.component';
import {JrpService} from './services/jrp.service';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    PacketsComponent,
    BreadCrumbComponent,
    NavigationComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule, BrowserAnimationsModule, CommonModule, MenubarModule, BreadcrumbModule
  ],
  providers: [JrpService, BreadCrumbService],
  bootstrap: [AppComponent]
})
export class AppModule {}
