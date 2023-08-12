import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  errorMessage: any;

  constructor(private http: HttpClient) { }

  getCurrencyValue(data: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const url = 'http://localhost:8080/currencies/get-current-currency-value-command';
    return this.http.post(url, data, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      alert(error.error);
      return throwError(()=> new Error('An error occurred:', error.error.message));
    } else {
      alert(error.error);
      return throwError(()=> new Error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`));
    }
  }
}
