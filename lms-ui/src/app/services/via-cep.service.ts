import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {ViaCepResponse} from '../models/via-cep-response.model';
import {Address} from '../models/address.model';

@Injectable({
  providedIn: 'root'
})
export class ViaCepService {
  private readonly apiUrl = `${environment.apiUrl}/via-cep`;

  constructor(private http: HttpClient) {
  }

  find(cep: string): Observable<Address> {
    const params = new HttpParams().set('cep', cep);
    return this.http.get<ViaCepResponse>(this.apiUrl, {params}).pipe(
      map(response => {
        if (response && !response.erro) {
          return {
            addressLine1: response.logradouro,
            addressLine2: response.complemento,
            city: response.localidade,
            state: response.estado,
            stateCode: response.uf
          };
        } else {
          throw new Error('CEP não encontrado.');
        }
      })
    );
  }
}
