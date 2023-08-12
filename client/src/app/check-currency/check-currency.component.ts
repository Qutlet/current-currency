import { Component } from '@angular/core';
import { CurrencyService } from './currency.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CurrencyResponse } from './currency-response';

@Component({
  selector: 'app-check-currency',
  templateUrl: './check-currency.component.html',
  styleUrls: ['./check-currency.component.css']
})
export class CheckCurrencyComponent {

  response: CurrencyResponse = {
    currency: '',
    value: 0
  }

  visability: boolean;

  currencyForm: FormGroup

  constructor(
    private currencyService: CurrencyService,
    private formBuilder: FormBuilder,
  ) {
    this.visability = false;
    this.currencyForm = this.formBuilder.group({
      name:  ['', Validators.required],
      currency:  ['', [Validators.required, Validators.maxLength(3), Validators.minLength(3)]]
    });
  }

  onSubmit(): void {
    if(this.currencyForm.invalid) {
      this.currencyForm.markAllAsTouched();
    } else {
      this.currencyService.getCurrencyValue( this.currencyForm.value)
        .subscribe((response: CurrencyResponse) => {
          this.response = response;
          this.visability = true;
          });
        this.currencyForm.reset();
    }
   }
   


  clear(): void {
    this.visability = false
  }


}