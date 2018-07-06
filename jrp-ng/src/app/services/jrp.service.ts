import {menuIems} from './data';
import {Injectable} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Injectable()
export class JrpService {

  constructor() {}

  getMenuItems(): MenuItem[] {
    return menuIems;
  }

  getCmecfMenuItems(): MenuItem[] {
    const cmecfMenuItems: MenuItem[] = [
      {label: 'Bankruptcy'},
      {label: 'Adversary'},
      {label: 'Query'},
      {label: 'Reports'},
      {label: 'Utilities'},
      {label: 'Help'},
      {label: 'What\'s New'},
      {label: 'Logout'}
    ];
    return cmecfMenuItems;
  }

}