import { SharedModule } from '../shared.module';
import { StateListComponent } from '../state/state-list.component';
import { StateService } from '../state/state.service';
import {CountryDetailComponent} from './country-detail.component';
import {CountryFilterPipe} from './country-filter.pipe';
import {CountryListComponent} from './country-list.component';
import {CountryRoutingModule} from './country-routing.module';
import {CountryService} from './country.service';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

@NgModule(
  {
    imports: [CountryRoutingModule, ReactiveFormsModule, CommonModule, FormsModule, SharedModule],
    declarations: [CountryListComponent, CountryDetailComponent, CountryFilterPipe],
    providers: [CountryService]
  }
)

export class CountryModule {}
