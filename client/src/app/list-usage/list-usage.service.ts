import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ListUsageService {


    errorMessage: any;

    constructor(private http: HttpClient) { }

    getUsage(): Observable<any> {
        const url = 'http://localhost:8080/currencies/requests';
        return this.http.get(url).pipe(
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