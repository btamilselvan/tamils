import {State} from '../state/state';
export class Country {
  code: string;
  name: string;
  state: State[];

  constructor(code: string, name: string, state: State[]) {
    this.code = code;
    this.name = name;
    this.state = state;
  }
}
