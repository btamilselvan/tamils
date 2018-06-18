import {JrpService} from '../services/jrp.service';
import {Component, OnInit, Input, ViewChild} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  items: MenuItem[];
  constructor(private jrpService: JrpService) {}

  ngOnInit() {
    this.items = this.jrpService.getMenuItems();
  }

}
