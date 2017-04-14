import { Component } from '@angular/core';
import { Data }                from './app.data';
import { AppService }         from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
   info = 'Please input a sequence of numbers separated my comma, without spaces.';
  title1 = 'Please input number array 1';
  title2 = 'Please input number array 2';
  clickMessage = '';
  values1 = '';
  values2 = '';
   changedValue1 = '';
     changedValue2 = '';
    
    constructor(
    private appService: AppService) { }
    
  onKey1(event: any) { // without type info
   this.values1 = event.target.value;
  }
   onKey2(event: any) { // without type info
    this.values2 = event.target.value;
  }
    completed()
    {
        this.clickMessage = 'DONE AGAIN';
    }
  submitValues() {
      
      let data =  new Data(this.values1,this.values2);
    this.appService.getSwappedData(data)
    .then(data => { this.changedValue1 = data.string1;
    this.changedValue2 = data.string2;} );
  }
    
  
}
