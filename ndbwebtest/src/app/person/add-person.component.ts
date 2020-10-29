import {City} from '../city/city';
import {District} from '../district/district';
import {State} from '../state/state';
import {StateDropDownComponent} from '../state/state-drop-down.component';
import {Component, ViewChild} from '@angular/core';
import {Person} from './person';
import {PersonService} from './person.service';
@Component({
  selector: 'app-add-person',
  templateUrl: './add-person.component.html'
})
export class AddPersonComponent {
  person = new Person();
  selectedState: State = null;
  selectedDistrict: District = null;
  selectedCity: City = null;
  message: string;
  success = true;
  @ViewChild(StateDropDownComponent, {static: true})
  stateDropDownComp: StateDropDownComponent;
  constructor(private personService: PersonService) {

  }
  addPerson() {
    this.person.state = this.selectedState.name;
    this.person.district = this.selectedDistrict.name;
    this.person.city = this.selectedCity.name;
    this.personService.addPerson(this.person).subscribe(newPerson => {
      this.message = newPerson.message;
      this.success = !(newPerson.error);
    });
  }
  reset() {
    this.person = new Person();
    this.selectedCity = null;
    this.selectedDistrict = null;
    this.selectedState = null;
    this.message = null;
    this.success = true;
    this.stateDropDownComp.selState = null;
  }
  onStateChange(state) {
    if (state) {
      this.selectedState = state;
    } else {
      this.selectedState = null;
    }
    this.selectedDistrict = null;
  }
  onDistrictChange(district) {
    if (district) {
      this.selectedDistrict = district;
    } else {
      this.selectedDistrict = null;
    }
  }
  onCityChange(event) {
    this.selectedCity = event;
  }
}
