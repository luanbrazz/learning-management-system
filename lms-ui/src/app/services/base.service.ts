  import { HttpClient } from '@angular/common/http';
  import { Observable } from 'rxjs';
  import { environment } from '../../environments/environment';

  export abstract class BaseService<T, ID> {
    protected apiUrl = environment.apiUrl;

    constructor(protected http: HttpClient, protected endpoint: string) {}

    findAll(): Observable<T[]> {
      return this.http.get<T[]>(`${this.apiUrl}/${this.endpoint}`);
    }

    findAllPaginated(page: number, size: number, sort: string): Observable<any> {
      const params = { page: page.toString(), size: size.toString(), sort: sort };
      return this.http.get<any>(`${this.apiUrl}/${this.endpoint}/page`, { params });
    }

    findById(id: ID): Observable<T> {
      return this.http.get<T>(`${this.apiUrl}/${this.endpoint}/${id}`);
    }

    save(entity: T): Observable<T> {
      return this.http.post<T>(`${this.apiUrl}/${this.endpoint}`, entity);
    }

    update(id: ID, entity: T): Observable<T> {
      return this.http.put<T>(`${this.apiUrl}/${this.endpoint}/${id}`, entity);
    }

    delete(id: ID): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${this.endpoint}/${id}`);
    }
  }
