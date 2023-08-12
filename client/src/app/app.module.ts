import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CheckCurrencyComponent } from './check-currency/check-currency.component';
import { ListUsageComponent } from './list-usage/list-usage.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', redirectTo: '/check-currency', pathMatch: 'full' },
  { path: 'check-currency', component: CheckCurrencyComponent },
  { path: 'list-usage', component: ListUsageComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    CheckCurrencyComponent,
    ListUsageComponent,
    NavigationBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
